package in.vs.time;

import java.math.BigInteger;

public class TotallyOrderedTimestamp implements Comparable<TotallyOrderedTimestamp> {
    // TODO
    private long time;
    private long PID;
    private long TID;

    public TotallyOrderedTimestamp(long time, long PID, long TID) {
        this.time = time;
        this.PID = PID;
        this.TID = TID;
    }


    @Override
    public int compareTo(TotallyOrderedTimestamp arg) {
        if (this.time < arg.time) {
            return -1;
        } else if (this.time > arg.time) {
            return 1;
        } else {
            if(this.PID < arg.PID) {
                return -1;
            } else if (this.PID > arg.PID){
                return 1;
            } else {
                if (this.TID < arg.TID) {
                    return -1;
                } else if(this.TID > arg.TID) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }

    public BigInteger asBigInteger() {
        return new BigInteger("" + this.time + PID + TID);
    }

    public long getTimestamp() {
        return this.time;
    }
}
