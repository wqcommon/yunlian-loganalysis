package com.yunlian.loganalysis.util;

/**
 * @author qiang.wen
 * @date 2018/8/9 15:38
 * <p>
 * twitter snowflake的实现，用于产生全局id
 */
public class GlobalIdGenerator {


    /**
     * 每一部分占用位数的默认值
     */
    private final static int MACHINE_BIT_NUM = 8;   //机器标识占用的位数
    private final static int IDC_BIT_NUM = 2;//数据中心占用的位数

    private final static long MACHINE_ID = 192;//机器id

    private final static long IDC_ID = 3;//idc id


    /**
     * 起始的时间戳
     */
    private final static long START_STAMP = 1533801659105L;

    /**
     * 可分配的位数
     */
    private final static int REMAIN_BIT_NUM = 26;

    /**
     * idc编号
     */
    private long idcId;

    /**
     * 机器编号
     */
    private long machineId;

    /**
     * 当前序列号
     */
    private long sequence = 0L;

    /**
     * 上次最新时间戳
     */
    private long lastStamp = -1L;

    /**
     * idc偏移量：一次计算出，避免重复计算
     */
    private int idcBitLeftOffset;

    /**
     * 机器id偏移量：一次计算出，避免重复计算
     */
    private int machineBitLeftOffset;

    /**
     * 时间戳偏移量：一次计算出，避免重复计算
     */
    private int timestampBitLeftOffset;

    /**
     * 最大序列值：一次计算出，避免重复计算
     */
    private int maxSequenceValue;


    private static GlobalIdGenerator idGenerator = new GlobalIdGenerator(IDC_BIT_NUM,MACHINE_BIT_NUM,IDC_ID,MACHINE_ID);


    public static GlobalIdGenerator getInstance(){
        return idGenerator;
    }


    private GlobalIdGenerator(int idcBitNum, int machineBitNum, long idcId, long machineId) {
        int sequenceBitNum = REMAIN_BIT_NUM - idcBitNum - machineBitNum;

        if (idcBitNum <= 0 || machineBitNum <= 0 || sequenceBitNum <= 0) {
            throw new IllegalArgumentException("error bit number");
        }

        this.maxSequenceValue = ~(-1 << sequenceBitNum);

        machineBitLeftOffset = sequenceBitNum;
        idcBitLeftOffset = idcBitNum + sequenceBitNum;
        timestampBitLeftOffset = idcBitNum + machineBitNum + sequenceBitNum;

        this.idcId = idcId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID
     */
    public synchronized long nextId() {
        long currentStamp = getTimeMill();
        if (currentStamp < lastStamp) {
            throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastStamp - currentStamp));
        }

        //新的毫秒，序列从0开始，否则序列自增
        if (currentStamp == lastStamp) {
            sequence = (sequence + 1) & this.maxSequenceValue;
            if (sequence == 0L) {
                //Twitter源代码中的逻辑是循环，直到下一个毫秒
                lastStamp = tilNextMillis();
//                throw new IllegalStateException("sequence over flow");
            }
        } else {
            sequence = 0L;
        }

        lastStamp = currentStamp;

        return (currentStamp - START_STAMP) << timestampBitLeftOffset | idcId << idcBitLeftOffset | machineId << machineBitLeftOffset | sequence;
    }

    private long getTimeMill() {
        return System.currentTimeMillis();
    }

    private long tilNextMillis() {
        long timestamp = getTimeMill();
        while (timestamp <= lastStamp) {
            timestamp = getTimeMill();
        }
        return timestamp;
    }

}
