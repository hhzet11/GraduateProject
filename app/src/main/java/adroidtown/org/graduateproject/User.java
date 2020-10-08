package adroidtown.org.graduateproject;

public class User {
    public String userName;
    public String userId;
    public String userPw;
    public String userType;
    public String userEmail;

    public User() {

    }

    public User(String userName, String userId, String userPw, String userType, String userEmail){
        this.userName = userName;
        this.userId = userId;
        this.userPw = userPw;
        this.userType = userType;
        this.userEmail = userEmail;
    }

    public String getUserName(){ return userName; }

    public void setUserName(){ this.userName = userName; }

    public String getUserId(){ return userId; }

    public void setUserId(){ this.userId = userId; }

    public String getUserPw(){ return userPw; }

    public void setUserPw(){ this.userPw = userPw; }

    public String getUserType(){ return userType; }

    public void setUserType(){ this.userType = userType; }

    public String getUserEmail(){ return userEmail; }

    public void setUserEmail(){ this.userEmail = userEmail; }

    public String toString(){
        return "User{" + "userName='" + userName +'\'' + ",userId='" + userId + '\'' +
                ", userPw='" + userPw + '\'' + ", userType='" + userType + '\'' + ", userEmail='" + userEmail + '\'' + '}';
    }


}
