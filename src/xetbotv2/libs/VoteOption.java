package xetbotv2.libs;

/**
  describes a typical poll option
*/
public class VoteOption implements Comparable{
  private String option;
  private int votes;
  
  public VoteOption(String option, int votes){
    this.option = option;
    this.votes = votes;
  }
  
  public VoteOption(String option){ this(option, 0); }
  
  public void addVote(){ votes++; }
  public int getVotes(){ return votes; }
  public String getOption() { return option; }
  
  public int compareTo(Object otherOne){
    VoteOption v = (VoteOption)otherOne;
    return votes - (v.getVotes());
  }
}