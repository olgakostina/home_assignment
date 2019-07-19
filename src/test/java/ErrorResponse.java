public class ErrorResponse {

    private Integer errorCode;
    private String errorKey;
    private String errorMessage;

    public void ErrorResponse() {

    }

    public ErrorResponse(Integer errorCode, String errorKey, String errorMessage) {
        this.errorCode = errorCode;
        this.errorKey = errorKey;
        this.errorMessage = errorMessage;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorKey(String errorKey) {
        this.errorKey = errorKey;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "errorCode=" + errorCode +
                ", errorKey='" + errorKey + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
