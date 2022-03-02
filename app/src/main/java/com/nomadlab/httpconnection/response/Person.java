package com.nomadlab.httpconnection.response;
//
// jsonObject.put("이름", "홍길동");
//         jsonObject.put("나이", 20);
//         jsonObject.put("직업", "개발자");
//         jsonObject.put("취미", "게임");
//         jsonObject.put("결혼여부", false);
public class Person {
    int 나이;
    String 직업;
    String 취미;
    boolean 결혼여부;

    @Override
    public String toString() {
        return "Person{" +
                "나이=" + 나이 +
                ", 직업='" + 직업 + '\'' +
                ", 취미='" + 취미 + '\'' +
                ", 결혼여부=" + 결혼여부 +
                '}';
    }
}
