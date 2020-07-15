import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Main17_07 {
    /**
     * 婴儿名字
     * 每年，政府都会公布一万个最常见的婴儿名字和它们出现的频率，也就是同名婴儿的数量。
     * 有些名字有多种拼法，例如，John 和 Jon 本质上是相同的名字，但被当成了两个名字公布出来。
     * 给定两个列表，一个是名字及对应的频率，另一个是本质相同的名字对。设计一个算法打印出每个真实名字的实际频率。
     * 注意，如果 John 和 Jon 是相同的，并且 Jon 和 Johnny 相同，则 John 与 Johnny 也相同，即它们有传递和对称性。
     * 在结果列表中，选择字典序最小的名字作为真实名字
     * 输入：names = ["John(15)","Jon(12)","Chris(13)","Kris(4)","Christopher(19)"],
     *      synonyms = ["(Jon,John)","(John,Johnny)","(Chris,Kris)","(Chris,Christopher)"]
     * 输出：["John(27)","Chris(36)"]
     * ["Fcclu(70)","Dnsay(72)","Qbmk(45)","Unsb(26)","Gauuk(75)","Gnplfi(109)","Hfp(97)","Obcbxb(124)","Ijveol(46)","Fpaf(219)",
     * "Qiy(26)","Mcnef(59)","Dhe(79)","Jfq(26)","Ebov(96)","Ofl(72)","Uvkdpn(71)","Avcp(41)","Chycnm(253)","Koaak(53)",
     * "Qyqifg(85)","Oltadg(95)","Mwwvj(70)","Naf(3)","Ibink(32)","Ucqh(51)","Mtz(72)","Ard(82)","Hcvcgc(97)","Knpuq(61)",
     * "Yeekgc(11)","Ntfr(70)","Bnea(179)","Weusps(79)","Nuq(61)","Drzsnw(87)","Chhmx(259)","Onnev(77)","Kufa(95)","Avmzs(39)",
     * "Okwuq(96)","Hljt(51)","Npilye(25)","Axwtno(57)","Kasgmw(95)","Nsgbth(26)","Nzaz(51)","Msyr(211)","Yjc(94)","Jvqg(47)",
     * "Alrksy(69)","Aeax(646)","Acohsf(86)","Csh(238)","Nekuam(17)","Kgabb(236)","Fvkhz(104)","Gbkq(77)","Dwifi(237)"]
     */
    //方法一：leetcode题解的方法。根据synonyms的元素实时调整map。非常巧妙的方法。
    public static String[] trulyMostPopular1(String[] names, String[] synonyms) {
        Map<String, Integer> map = new HashMap<>();
        Map<String, String> unionMap = new HashMap<>();     //并查集， key(子孙)->value(祖宗)
        for (String name : names) {     //统计频率
            int idx1 = name.indexOf('(');
            int idx2 = name.indexOf(')');
            int frequency = Integer.parseInt(name.substring(idx1 + 1, idx2));
            map.put(name.substring(0, idx1), frequency);
        }
        for (String pair : synonyms) {  //union同义词
            int idx = pair.indexOf(',');
            String name1 = pair.substring(1, idx);  //前名字
            String name2 = pair.substring(idx + 1, pair.length() - 1);  //后名字
            while (unionMap.containsKey(name1)) {   //找name1祖宗
                name1 = unionMap.get(name1);
            }
            while (unionMap.containsKey(name2)) {   //找name2祖宗
                name2 = unionMap.get(name2);
            }
            if(!name1.equals(name2)){   //祖宗不同，要合并
                int frequency = map.getOrDefault(name1, 0) + map.getOrDefault(name2, 0);    //出现次数是两者之和
                String trulyName = name1.compareTo(name2) < 0 ? name1 : name2;
                String nickName = name1.compareTo(name2) < 0 ? name2 : name1;
                unionMap.put(nickName, trulyName);      //小名作为大名的分支，即大名是小名的祖宗
                map.remove(nickName);       //更新一下数据
                map.put(trulyName, frequency);
            }
        }
        String[] res = new String[map.size()];
        int index = 0;
        for (String name : map.keySet()) {
            StringBuilder sb = new StringBuilder(name);
            sb.append('(');
            sb.append(map.get(name));
            sb.append(')');
            res[index++] = sb.toString();
        }
        return res;
    }
    //方法二：我写的方法，虽然正确，但是因为采用了双层for结构，所以时间复杂度较高，导致在运行大数据量时会超时
    static HashMap<String, Integer> map1;
    static HashMap<String, Integer> map2;
    static int value;
    public static String[] trulyMostPopular2(String[] names, String[] synonyms) {
        //int sum = 0;
        map1 = new HashMap<>();
        map2 = new HashMap<>();
        value = 0;
        ArrayList<ArrayList<String>> keylists = new ArrayList<>();
        for(String name:names){
            saveNameAndTimes(name.trim());
        }
        for(String synonym :synonyms){
            classify(synonym.trim());
        }
        for(int i = 0; i < value; i++){
            ArrayList<String> keylist = getKey(map2, i);
            if(keylist.size() != 0){
                Collections.sort(keylist);
                keylists.add(keylist);
            }
        }
        ArrayList<String> reslist = new ArrayList<>();
        for (ArrayList<String> keylist : keylists) {
            int ans = 0;
            for (String s : keylist) {
                ans += map1.getOrDefault(s, 0);
            }
            reslist.add(keylist.get(0) + "(" + ans + ")");
            //sum = sum + ans;
        }
        //遍历map1，将那些没有别名的名字及次数放入reslist中
        map1.forEach((k, v) ->{if(!map2.containsKey(k)){
            reslist.add(k +"("+ v +")");
        }});
        //System.out.println(sum);
        String[] res = new String[reslist.size()];
        for(int i = 0; i < reslist.size(); i++){
            res[i] = reslist.get(i);
        }
        return res;
    }

    public static void saveNameAndTimes(String str){
        int i1 = 0;
        int i2 = 0;
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) == '('){
                i1 = i;
            }
            if(str.charAt(i) == ')'){
                i2 = i;
            }
        }
        map1.put(str.substring(0, i1).trim(), Integer.parseInt(str.substring(i1 + 1, i2).trim()));
    }

    public static void classify(String str){
        int i1 = 0;
        int i2 = 0;
        int i3 = 0;
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) == '('){
                i1 = i;
            }
            if(str.charAt(i) == ','){
                i2 = i;
            }
            if(str.charAt(i) == ')'){
                i3 = i;
            }
        }
        String str1 = str.substring(i1 + 1, i2).trim();
        String str2 = str.substring(i2 + 1, i3).trim();
        //判断map2中之前是否存过str1
        if(map2.containsKey(str1) && !map2.containsKey(str2)){
            ///如果map2中有str1，但没有str2，则put str2， 值为str1的值
            map2.put(str2, map2.get(str1));
        }
        else if(!map2.containsKey(str1) && map2.containsKey(str2)){
            ///如果map2中没有str1，但有str2，则put str1， 值为str2的值
            map2.put(str1, map2.get(str2));
        }
        else if(map2.containsKey(str1) && map2.containsKey(str2)){
            ///如果map2中既有str1，又有str2，有一种可能是 (Qnaakk,Msyr)..."(Wfmspz,Owk)"..."(Msyr,Owk)"
            int v1 = map2.get(str1);
            int v2 = map2.get(str2);
            if(v1 != v2){
                if(v1 < v2){
                    ArrayList<String> needToChanges = getKey(map2, v2);
                    for(String needToChange: needToChanges){
                        map2.replace(needToChange, v1);
                    }
                }else{
                    ArrayList<String> needToChanges = getKey(map2, v1);
                    for(String needToChange: needToChanges){
                        map2.replace(needToChange, v2);
                    }
                }
            }
        }
        else if(!map2.containsKey(str1) && !map2.containsKey(str2)){
            ///如果map2中没有str1，也没有str2，则put str1， 值为str2的值
            map2.put(str1, value);
            map2.put(str2, value);
            value++;
        }
    }

    /**
     * 作用：根据value查找key
     * @param map map域
     * @param value 要查找的value
     * @return 返回一个ArrayList<String>
     */
    public static ArrayList<String> getKey(HashMap<String, Integer> map, Object value){
        ArrayList<String> keyList = new ArrayList<>();
        for(String key: map.keySet()){
            if(map.get(key).equals(value)){
                keyList.add(key);
            }
        }
        return keyList;
    }

    public static void main(String[] args) {
        String[] names = {"Fcclu(70)","Ommjh(63)","Dnsay(60)","Qbmk(45)","Unsb(26)","Gauuk(75)","Wzyyim(34)","Bnea(55)","Kri(71)","Qnaakk(76)",
                "Gnplfi(68)","Hfp(97)","Qoi(70)","Ijveol(46)","Iidh(64)","Qiy(26)","Mcnef(59)","Hvueqc(91)","Obcbxb(54)","Dhe(79)",
                "Jfq(26)","Uwjsu(41)","Wfmspz(39)","Ebov(96)","Ofl(72)","Uvkdpn(71)","Avcp(41)","Msyr(9)","Pgfpma(95)","Vbp(89)",
                "Koaak(53)","Qyqifg(85)","Dwayf(97)","Oltadg(95)","Mwwvj(70)","Uxf(74)","Qvjp(6)","Grqrg(81)","Naf(3)","Xjjol(62)",
                "Ibink(32)","Qxabri(41)","Ucqh(51)","Mtz(72)","Aeax(82)","Kxutz(5)","Qweye(15)","Ard(82)","Chycnm(4)","Hcvcgc(97)",
                "Knpuq(61)","Yeekgc(11)","Ntfr(70)","Lucf(62)","Uhsg(23)","Csh(39)","Txixz(87)","Kgabb(80)","Weusps(79)","Nuq(61)",
                "Drzsnw(87)","Xxmsn(98)","Onnev(77)","Owh(64)","Fpaf(46)","Hvia(6)","Kufa(95)","Chhmx(66)","Avmzs(39)","Okwuq(96)",
                "Hrschk(30)","Ffwni(67)","Wpagta(25)","Npilye(14)","Axwtno(57)","Qxkjt(31)","Dwifi(51)","Kasgmw(95)","Vgxj(11)","Nsgbth(26)",
                "Nzaz(51)","Owk(87)","Yjc(94)","Hljt(21)","Jvqg(47)","Alrksy(69)","Tlv(95)","Acohsf(86)","Qejo(60)","Gbclj(20)",
                "Nekuam(17)","Meutux(64)","Tuvzkd(85)","Fvkhz(98)","Rngl(12)","Gbkq(77)","Uzgx(65)","Ghc(15)","Qsc(48)","Siv(47)"};
        String[] synonyms = {"(Gnplfi,Qxabri)","(Uzgx,Siv)","(Bnea,Lucf)","(Qnaakk,Msyr)","(Grqrg,Gbclj)",
                "(Uhsg,Qejo)","(Csh,Wpagta)","(Xjjol,Lucf)","(Qoi,Obcbxb)","(Npilye,Vgxj)",
                "(Aeax,Ghc)","(Txixz,Ffwni)","(Qweye,Qsc)","(Kri,Tuvzkd)","(Ommjh,Vbp)",
                "(Pgfpma,Xxmsn)","(Uhsg,Csh)","(Qvjp,Kxutz)","(Qxkjt,Tlv)","(Wfmspz,Owk)",
                "(Dwayf,Chycnm)","(Iidh,Qvjp)","(Dnsay,Rngl)","(Qweye,Tlv)","(Wzyyim,Kxutz)",
                "(Hvueqc,Qejo)","(Tlv,Ghc)","(Hvia,Fvkhz)","(Msyr,Owk)","(Hrschk,Hljt)",
                "(Owh,Gbclj)","(Dwifi,Uzgx)","(Iidh,Fpaf)","(Iidh,Meutux)","(Txixz,Ghc)",
                "(Gbclj,Qsc)","(Kgabb,Tuvzkd)","(Uwjsu,Grqrg)","(Vbp,Dwayf)","(Xxmsn,Chhmx)",
                "(Uxf,Uzgx)"};
        String[] strings = trulyMostPopular1(names, synonyms);
        //System.out.println(names.length);
        //System.out.println(strings.length);
        for(int i = 0; i < strings.length; i++){
            System.out.print(strings[i]);
            if(i < strings.length - 1){
                System.out.print(", ");
            }
        }
    }
}
