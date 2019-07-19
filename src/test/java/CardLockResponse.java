public class CardLockResponse {
    private String lockingReason;

    public void CardLockResponse() {

    }

    public CardLockResponse(String lockingReason) {
        this.lockingReason = lockingReason;
    }

    public void setLockingReason(String lockingReason) {
        this.lockingReason = lockingReason;
    }

    @Override
    public String toString() {
        return "CardLockResponse{" +
                "lockingReason='" + lockingReason + '\'' +
                '}';
    }
}

