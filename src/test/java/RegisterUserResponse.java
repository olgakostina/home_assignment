public class RegisterUserResponse {
    private String loginName;
    private String status;
    private String pan;

    public void RegisterUserResponse(){

    }

    public RegisterUserResponse(String loginName, String status, String pan) {
        this.loginName = loginName;
        this.status = status;
        this.pan = pan;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    @Override
    public String toString() {
        return "RegisterUserResponse{" +
                "loginName='" + loginName + '\'' +
                ", status='" + status + '\'' +
                ", pan='" + pan + '\'' +
                '}';
    }
}
