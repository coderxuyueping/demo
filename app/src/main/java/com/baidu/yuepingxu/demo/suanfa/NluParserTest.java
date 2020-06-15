package com.baidu.yuepingxu.demo.suanfa;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuyueping
 * @date 2020-04-11
 * @describe
 */
public class NluParserTest {
    static Map<String, SlotNode> dictionary = new HashMap<>();
    static List<String> findSlots = new ArrayList<>();

    public static void main(String[] args) {
        List<String> baseWord = new ArrayList<>();
        baseWord.add("打开天窗_da-kai-tian-chuan_vopenwindow");
        baseWord.add("打开车窗_da-kai-che-chuan_vopencarwindow");
        baseWord.add("播放音乐_bo-fang-ying-yue_vplaymusic");
        baseWord.add("打开天下的窗_da-kai-tian-xia-de-chuan_vopenallwindow");
        initDictionary(baseWord);

        // 查找槽位slot 一句话中可能有好几个槽位
        findSlots.clear();

        findSlot(0);

    }

    // 查找规则：找到第一个index出现在字典树中，然后找到这棵树下最后一个节点，之后在从下一段查找
    private static void findSlot(int index) {
        // 转为拼音
        String[] pinyins = {"wo", "yao", "da", "kai", "tian", "chuan", "he", "bo", "fang", "ying", "yue", "d","a","bo", "fang", "a","ying", "yue"};
        SlotNode root = null;
        while (root == null && index < pinyins.length) {
            // 找到不为空的有效槽位
            root = dictionary.get(pinyins[index]);
            index++;
        }
        if (root != null) {
            findValidSlot(root, pinyins, index);
        }
    }

    // 找到相连的有效槽位
    private static void findValidSlot(SlotNode slotNode, String[] pinyins, int index) {
        if (slotNode.slot != null) {
            findSlots.add(slotNode.slot);
            return;
        }
        for (int i = index; i < pinyins.length; i++) {
            SlotNode temp = slotNode.getChildens().get(pinyins[i]);
            if (temp != null && temp.slot == null) {
                // 有这个节点并且不是最后一个，继续
                slotNode = temp;
                continue;
            } else if (temp != null && temp.slot != null) {
                // 保存slot
                findSlots.add(temp.slot);
            } else {
                // 继续findslot找到下一个有效槽位解析
                findSlot(i);
                break;
            }
        }
    }

    public static void initDictionary(List<String> words) {
        for (String word : words) {
            String[] temp = word.split("_");
            // 第一个是汉字，第二个是拼音，第三个是槽位
            String[] pinyinS = temp[1].split("-");
            // 取出第一个拼音看是否有在字典里面
            if (dictionary.containsKey(pinyinS[0])) {
                // 插入到树里, 需要找到是哪个树节点
                insertToDictionary(dictionary.get(pinyinS[0]), temp[0], pinyinS, temp[2]);
            } else {
                //新建树
                createDictionary(temp[0], pinyinS, temp[2]);
            }
        }

    }

    private static void createDictionary(String word, String[] pinyinS, String slot) {
        SlotNode parentNode = new SlotNode();
        parentNode.setPinyin(pinyinS[0]);
        parentNode.setHanzi(String.valueOf(word.charAt(0)));
        dictionary.put(pinyinS[0], parentNode);
        //如果只有一个
        if (pinyinS.length == 1) {
            parentNode.setSlot(slot);
            return;
        }

        // 给parent添加子树
        for (int i = 1; i < pinyinS.length; i++) {
            SlotNode node = new SlotNode();
            node.setPinyin(pinyinS[i]);
            node.setHanzi(String.valueOf(word.charAt(i)));
            // 最后一个添加slot
            if (i == pinyinS.length - 1) {
                node.setSlot(slot);
            }
            parentNode.addChild(pinyinS[i], node);
            parentNode = node;
        }
    }

    private static void insertToDictionary(SlotNode slotNode, String word, String[] pinyinS, String slot) {
        for (int i = 1; i < pinyinS.length; i++) {
            // 判断slotNode的child是否包含了下一个拼音,我们需要找到不包含的那个去添加
            SlotNode child = slotNode.getChildens().get(pinyinS[i]);
            if (child != null) {
                // 最后一个添加slot
                if (i == pinyinS.length - 1) {
                    child.setSlot(slot);
                }
                slotNode = child;
            } else {
                SlotNode node = new SlotNode();
                node.setPinyin(pinyinS[i]);
                node.setHanzi(String.valueOf(word.charAt(i)));
                // 最后一个添加slot
                if (i == pinyinS.length - 1) {
                    node.setSlot(slot);
                }
                slotNode.addChild(pinyinS[i], node);
                slotNode = node;
            }
        }
    }
}


class SlotNode {
    String pinyin;
    String hanzi;
    String slot;
    Map<String, SlotNode> childens;

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getHanzi() {
        return hanzi;
    }

    public void setHanzi(String hanzi) {
        this.hanzi = hanzi;
    }

    public Map<String, SlotNode> getChildens() {
        return childens;
    }

    public void setChildens(Map<String, SlotNode> childens) {
        this.childens = childens;
    }

    public SlotNode() {
        childens = new HashMap<>();
    }


    public void addChild(String key, SlotNode node) {
        childens.put(key, node);
    }

}