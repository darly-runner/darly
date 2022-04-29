package com.darly.db.entity.match;

public interface MatchTitleMapping {

    String getHostNickname();
    String getHostImage();
    Long getMatchId();
    String getMatchTitle();
    Short getMatchMaxPerson();
    Short getMatchCurPerson();
    Float getMatchGoalDistance();
    String getMatchDate();
    String getMatchStartTime();
    Character getMatchStatus();

//    private String hostNickname;
//    private String hostImage;
//    private Long matchId;
//    private String matchTitle;
//    private Short matchMaxPerson;
//    private Short matchCurPerson;
//    private Float matchGoalDistance;
//    private String matchDate;
//    private String matchStartTime;
//    private Character matchStatus;
//
//    @QueryProjection
//    public MatchTitleMapping(String hostNickname, String hostImage, Long matchId, String matchTitle, Short matchMaxPerson, Short matchCurPerson, Float matchGoalDistance, Long matchDate, Long matchStartTime, Character matchStatus) {
//        this.hostNickname = hostNickname;
//        this.hostImage = hostImage;
//        this.matchId = matchId;
//        this.matchTitle = matchTitle;
//        this.matchMaxPerson = matchMaxPerson;
//        this.matchCurPerson = matchCurPerson;
//        this.matchGoalDistance = matchGoalDistance;
//        this.matchDate = new SimpleDateFormat("yyyy년 MM월 dd일").format(new Date(matchDate * 1000));
//        this.matchStartTime = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분").format(new Date(matchStartTime * 1000));
//        this.matchStatus = matchStatus;
//    }
}
