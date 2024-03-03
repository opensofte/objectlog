package org.sweetie.objectlog.core.utils;

import cn.hutool.json.JSONUtil;
import difflib.Delta;
import difflib.DiffRow;
import difflib.DiffRowGenerator;
import difflib.DiffUtils;
import difflib.Patch;
import org.sweetie.objectlog.core.enums.PartType;
import org.sweetie.objectlog.core.constant.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StringDiffUtil {
    private StringDiffUtil() {
    }

    /**
     * 对比两个 text对象，或者html片段的不同，生成类似于gitDiff的html片段
     *
     * @param oldStringList 旧值
     * @param newStringList 新值
     * @return 类似于gitDiff的html片段
     */
    public static String diffText(List<String> oldStringList, List<String> newStringList) {
        // 获得文件的不同之处
        Patch patch = DiffUtils.diff(oldStringList, newStringList);
        DiffRowGenerator.Builder builder = new DiffRowGenerator.Builder();
        builder.showInlineDiffs(false).columnWidth(Integer.MAX_VALUE);
        DiffRowGenerator generator = builder.build();

        Map<Integer, List<DiffRow>> diffRowMap = new LinkedHashMap<>();

        // 获得变化的数据
        for (Object delta : patch.getDeltas()) {
            List<String> originalLines = (List<String>) ((Delta) delta).getOriginal().getLines();
            List<String> revisedLines = (List<String>) ((Delta) delta).getRevised().getLines();
            List<DiffRow> generateDiffRows = generator.generateDiffRows(originalLines, revisedLines);
            // 变化的位置
            int leftPos = ((Delta) delta).getOriginal().getPosition();
            int rightPos = ((Delta) delta).getRevised().getPosition();
            // 根据改变的位置做分类
            for (DiffRow row : generateDiffRows) {
                List<DiffRow> diffRowList = diffRowMap.get(leftPos);
                if (diffRowList == null) {
                    diffRowList = new ArrayList<DiffRow>();
                }
                diffRowList.add(row);
                diffRowMap.put(leftPos, diffRowList);
            }
        }

        Map<String, Object> textDiffMap = new HashMap<>();
        textDiffMap.put("version", "1.0.0");
        List<Fragment> diffFragmentList = new ArrayList<>();

        // 遍历map
        Set<Map.Entry<Integer, List<DiffRow>>> entrySet = diffRowMap.entrySet();
        for (Map.Entry<Integer, List<DiffRow>> entry : entrySet) {
            Integer pos = entry.getKey();
            List<DiffRow> diffRowList = entry.getValue();
            Fragment fragment = new Fragment(pos + 1);
            List<Part> partList = new ArrayList<>();
            for (DiffRow row : diffRowList) {
                DiffRow.Tag tag = row.getTag();
                if (tag == DiffRow.Tag.INSERT) {
                    Part part = new Part(PartType.ADD, replaceImgTag(row.getNewLine()));
                    partList.add(part);
                } else if (tag == DiffRow.Tag.CHANGE) {
                    if (!row.getOldLine().trim().isEmpty()) {
                        Part part = new Part(PartType.CHANGE_OLD, replaceImgTag(row.getOldLine()));
                        partList.add(part);
                    }
                    if (!row.getNewLine().trim().isEmpty()) {
                        Part part = new Part(PartType.CHANGE_NEW, replaceImgTag(row.getNewLine()));
                        partList.add(part);
                    }
                } else if (tag == DiffRow.Tag.DELETE) {
                    Part part = new Part(PartType.DEL, replaceImgTag(row.getOldLine()));
                    partList.add(part);
                }
            }
            fragment.setPartList(partList);
            diffFragmentList.add(fragment);

        }
        textDiffMap.put("content", diffFragmentList);
        return JSONUtil.toJsonStr(textDiffMap);
    }

    private static String replaceImgTag(String s) {
        return s.replaceAll(Constant.IMG_LEFT_PLACE_HOLDER, "<img ").replaceAll(Constant.IMG_RIGHT_PLACE_HOLDER, " >");
    }

    public static class Fragment {
        private Integer lineNumber;
        private List<Part> partList;

        Fragment(Integer lineNumber) {
            this.lineNumber = lineNumber;
        }

        Integer getLineNumber() {
            return lineNumber;
        }

        void setLineNumber(Integer lineNumber) {
            this.lineNumber = lineNumber;
        }

        List<Part> getPartList() {
            return partList;
        }

        void setPartList(List<Part> partList) {
            this.partList = partList;
        }
    }

    public static class Part {
        private PartType partType;
        private String partContent;

        Part(PartType partType) {
            this.partType = partType;
        }

        Part(PartType partType, String partContent) {
            this.partType = partType;
            this.partContent = partContent;
        }

        PartType getPartType() {
            return partType;
        }

        void setPartType(PartType partType) {
            this.partType = partType;
        }

        String getPartContent() {
            return partContent;
        }

        void setPartContent(String partContent) {
            this.partContent = partContent;
        }
    }

}
