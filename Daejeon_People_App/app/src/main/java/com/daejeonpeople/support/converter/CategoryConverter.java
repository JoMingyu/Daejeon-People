package com.daejeonpeople.support.converter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JoMingyu on 2017-06-20.
 */

public class CategoryConverter {
    private static Map<String, String> categoryMap = new HashMap<>();

    static {
        categoryMap.put("A01010100", "국립공원");
        categoryMap.put("A01010200", "도립공원");
        categoryMap.put("A01010300", "군립공원");
        categoryMap.put("A01010400", "산");
        categoryMap.put("A01010500", "자연생태관광지");
        categoryMap.put("A01010600", "자연휴양림");
        categoryMap.put("A01010700", "수목원");
        categoryMap.put("A01010800", "폭포");
        categoryMap.put("A01010900", "계곡");
        categoryMap.put("A01011000", "약수터");
        categoryMap.put("A01011100", "해안절경");
        categoryMap.put("A01011200", "해수욕장");
        categoryMap.put("A01011300", "섬");
        categoryMap.put("A01011400", "항구/포구");
        categoryMap.put("A01011500", "어촌");
        categoryMap.put("A01011600", "등대");
        categoryMap.put("A01011700", "호수");
        categoryMap.put("A01011800", "강");
        categoryMap.put("A01011900", "동굴");
        categoryMap.put("A01020100", "희귀동/식물");
        categoryMap.put("A01020200", "기암괴석");
        categoryMap.put("A02010100", "고궁");
        categoryMap.put("A02010200", "성");
        categoryMap.put("A02010300", "문");
        categoryMap.put("A02010400", "고택");
        categoryMap.put("A02010500", "생가");
        categoryMap.put("A02010600", "민속마을");
        categoryMap.put("A02010700", "유적지/사적지");
        categoryMap.put("A02010800", "사찰");
        categoryMap.put("A02010900", "종교성지");
        categoryMap.put("A02011000", "안보관광");
        categoryMap.put("A02020100", "유원지");
        categoryMap.put("A02020200", "관광단지");
        categoryMap.put("A02020300", "온천/욕장/스파");
        categoryMap.put("A02020400", "이색찜질방");
        categoryMap.put("A02020500", "헬스투어");
        categoryMap.put("A02020600", "테마공원");
        categoryMap.put("A02020700", "공원");
        categoryMap.put("A02020800", "유람선/잠수함관광");
        categoryMap.put("A02030100", "농/산/어촌 체험");
        categoryMap.put("A02030200", "전통체험");
        categoryMap.put("A02030300", "산사체험");
        categoryMap.put("A02030400", "이색체험");
        categoryMap.put("A02030500", "관광농원");
        categoryMap.put("A02030600", "이색거리");
        categoryMap.put("A02040100", "제철소");
        categoryMap.put("A02040200", "조선소");
        categoryMap.put("A02040300", "공단");
        categoryMap.put("A02040400", "발전소");
        categoryMap.put("A02040500", "광산");
        categoryMap.put("A02040600", "식음료");
        categoryMap.put("A02040700", "화학/금속");
        categoryMap.put("A02040800", "기타");
        categoryMap.put("A02040900", "전자/반도체");
        categoryMap.put("A02041000", "자동차");
        categoryMap.put("A02050100", "다리/대교");
        categoryMap.put("A02050200", "기념탑/기념비/전망대");
        categoryMap.put("A02050300", "분수");
        categoryMap.put("A02050400", "동상");
        categoryMap.put("A02050500", "터널");
        categoryMap.put("A02050600", "유명건물");
    }
}
