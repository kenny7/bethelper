package analyzer.outputData;

public interface OutputData {

    <T> void setOutputData(T t);

    <T> T getOutputData();

    void submitDataOutput();
}
