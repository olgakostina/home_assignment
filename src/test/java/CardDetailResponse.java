public class CardDetailResponse {
    private String holderName;
    private String pan;
    private String status;

    public void CardDetailResponse(){

    }

    public CardDetailResponse(String holderName, String pan, String status) {
        this.holderName = holderName;
        this.pan = pan;
        this.status = status;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CardDetailResponse{" +
                "holderName='" + holderName + '\'' +
                ", pan='" + pan + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
