package com.github.ontio.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.ontio.sdk.exception.SDKException;
import org.junit.Test;

import java.math.BigInteger;

import static com.github.ontio.common.Helper.BigIntFromNeoBytes;
import static com.github.ontio.common.Helper.BigIntToNeoBytes;
import static org.junit.Assert.*;

public class HelperTest {
    @Test
    public void parseBalancesArrayWithNestedArrayAndEmptyBalances() throws SDKException {
        String jsonString = "[[\"6f555344\", \"\"], [\"6f4b4553\", \"\"], [\"6f425344\", \"\"]]";
        JSONArray jsonArray = JSONObject.parseArray(jsonString);

        String expectedJsonString = "[[\"6f555344\", \"0\"], [\"6f4b4553\", \"0\"], [\"6f425344\", \"0\"]]";
        JSONArray expected = JSONObject.parseArray(expectedJsonString);

        String resultJsonString = Helper.parseBalancesArray(jsonArray);
        JSONArray result = JSONObject.parseArray(resultJsonString);

        assertEquals(expected, result);
    }

    private String getHexBalance(String number) {
        return Helper.toHexString(Helper.BigIntToNeoBytes(new BigInteger(number)));
    }

    @Test
    public void parseBalancesArrayWithNestedArrayAndNonZeroBalances() throws SDKException {
        String firstBalance = getHexBalance("111");
        String secondBalance = getHexBalance("222");
        String thirdBalance = getHexBalance("333");

        String jsonStringToFormat = "[[\"6f555344\", \"%s\"], [\"6f4b4553\", \"%s\"], [\"6f425344\", \"%s\"]]";
        String jsonString = String.format(jsonStringToFormat, firstBalance, secondBalance, thirdBalance);
        JSONArray jsonArray = JSONObject.parseArray(jsonString);

        String expectedJsonString = "[[\"6f555344\", \"111\"], [\"6f4b4553\", \"222\"], [\"6f425344\", \"333\"]]";
        JSONArray expected = JSONObject.parseArray(expectedJsonString);

        String resultJsonString = Helper.parseBalancesArray(jsonArray);
        JSONArray result = JSONObject.parseArray(resultJsonString);

        assertEquals(expected, result);
    }

    private String getHexSymbol(String symbol) {
        return Helper.toHexString(symbol.getBytes());
    }

    @Test
    public void parseBalancesArrayEmptyBalances() throws SDKException {
        String jsonString = "[\"\", \"\", \"\"]";
        JSONArray jsonArray = JSONObject.parseArray(jsonString);

        JSONArray expected = JSONObject.parseArray("[[\"00\", \"0\"], [\"01\", \"0\"], [\"02\", \"0\"]]");

        String resultJsonString = Helper.parseBalancesArray(jsonArray);
        JSONArray result = JSONObject.parseArray(resultJsonString);

        assertEquals(expected, result);
    }

    @Test
    public void parseBalancesArrayEmptyArray() throws SDKException {
        JSONArray jsonArray = JSONObject.parseArray("[]");
        JSONArray expected = JSONObject.parseArray("[]");

        String resultJsonString = Helper.parseBalancesArray(jsonArray);
        JSONArray result = JSONObject.parseArray(resultJsonString);

        assertEquals(expected, result);
    }

    @Test(expected = SDKException.class)
    public void parseBalancesArrayWithCorruptedData() throws SDKException {
        JSONArray jsonArray = JSONObject.parseArray("[1, 2, 3]");

        String resultJsonString = Helper.parseBalancesArray(jsonArray);
        JSONObject.parseArray(resultJsonString);
    }

    @Test
    public void parseBalancesArrayOnyxchainAssets() throws SDKException {
        // For AZPrhqNeRzC6UsDf8vfZueSTYAiB5W71gz
        final String response = "[[\"6f555344\",\"80d491a717\"],[\"6f4b4553\",\"ffdbff0514\"],[\"6f425344\",\"\"]," +
                                 "[\"6f425a44\",\"\"],[\"6f434846\",\"\"],[\"6f434c46\",\"\"],[\"6f424946\",\"\"]," +
                                 "[\"6f42414d\",\"\"],[\"6f42474e\",\"\"],[\"6f424d44\",\"\"],[\"6f434446\",\"\"]," +
                                 "[\"6f41464e\",\"\"],[\"6f415747\",\"\"],[\"6f42524c\",\"\"],[\"6f42544e\",\"\"]," +
                                 "[\"6f434f50\",\"\"],[\"6f414544\",\"\"],[\"6f425952\",\"\"],[\"6f434144\",\"\"]," +
                                 "[\"6f414c4c\",\"\"],[\"6f424f42\",\"\"],[\"6f434c50\",\"\"],[\"6f415253\",\"\"]," +
                                 "[\"6f425443\",\"\"],[\"6f414e47\",\"\"],[\"6f424244\",\"\"],[\"6f424454\",\"\"]," +
                                 "[\"6f424844\",\"\"],[\"6f425750\",\"\"],[\"6f42594e\",\"\"],[\"6f434e59\",\"\"]," +
                                 "[\"6f414d44\",\"\"],[\"6f415544\",\"\"],[\"6f415a4e\",\"\"],[\"6f424e44\",\"\"]," +
                                 "[\"6f414f41\",\"\"],[\"6f435550\",\"\"],[\"6f455552\",\"40f76a12\"]," +
                                 "[\"6f464a44\",\"\"],[\"6f475451\",\"\"],[\"6f454750\",\"\"],[\"6f464b50\",\"\"]," +
                                 "[\"6f474e46\",\"\"],[\"6f484b44\",\"\"],[\"6f484e4c\",\"\"],[\"6f45524e\",\"\"]," +
                                 "[\"6f455442\",\"\"],[\"6f474250\",\"\"],[\"6f474853\",\"\"],[\"6f435645\",\"\"]," +
                                 "[\"6f435a4b\",\"\"],[\"6f445a44\",\"\"],[\"6f474d44\",\"\"],[\"6f435543\",\"\"]," +
                                 "[\"6f47454c\",\"\"],[\"6f474750\",\"\"],[\"6f435243\",\"\"],[\"6f444b4b\",\"\"]," +
                                 "[\"6f444a46\",\"\"],[\"6f444f50\",\"\"],[\"6f474950\",\"\"],[\"6f475944\",\"\"]," +
                                 "[\"6f4b5257\",\"\"],[\"6f4e4144\",\"\"],[\"6f4e494f\",\"\"],[\"6f4c414b\",\"\"]," +
                                 "[\"6f4d5952\",\"\"],[\"6f494452\",\"\"],[\"6f494c53\",\"\"],[\"6f4d4e54\",\"\"]," +
                                 "[\"6f4d444c\",\"\"],[\"6f485546\",\"\"],[\"6f4d5552\",\"\"],[\"6f4e474e\",\"\"]," +
                                 "[\"6f494e52\",\"\"],[\"6f4d5652\",\"\"],[\"6f4d5a4e\",\"\"],[\"6f4b4753\",\"\"]," +
                                 "[\"6f485447\",\"\"],[\"6f4c4250\",\"\"],[\"6f4c564c\",\"\"],[\"6f4d4144\",\"\"]," +
                                 "[\"6f49534b\",\"\"],[\"6f4b5057\",\"\"],[\"6f4c544c\",\"\"],[\"6f4d4b44\",\"\"]," +
                                 "[\"6f4b4d46\",\"\"],[\"6f4d4741\",\"\"],[\"6f4b5944\",\"\"],[\"6f4c4b52\",\"\"]," +
                                 "[\"6f4c534c\",\"\"],[\"6f4d4d4b\",\"\"],[\"6f4d574b\",\"\"],[\"6f4d584e\",\"\"]," +
                                 "[\"6f4d4f50\",\"\"],[\"6f4d524f\",\"\"],[\"6f4c5244\",\"\"],[\"6f4c5944\",\"\"]," +
                                 "[\"6f4e4f4b\",\"\"],[\"6f4a4550\",\"\"],[\"6f4a4f44\",\"\"],[\"6f4b5744\",\"\"]," +
                                 "[\"6f584146\",\"\"],[\"6f554758\",\"008e1b7eda07\"],[\"6f5a4152\",\"\"]," +
                                 "[\"6f584f46\",\"\"],[\"6f534352\",\"\"],[\"6f534c4c\",\"\"],[\"6f545a53\",\"\"]," +
                                 "[\"6f534447\",\"\"],[\"6f535a4c\",\"\"],[\"6f525746\",\"00a0db215d\"]," +
                                 "[\"6f4a5059\",\"\"],[\"6f534f53\",\"\"],[\"6f5a4d57\",\"7f668c1601\"]]";

        final String expected = "[[\"6f555344\",\"101595600000\"],[\"6f4b4553\",\"85999999999\"],[\"6f425344\",\"0\"]," +
                                 "[\"6f425a44\",\"0\"],[\"6f434846\",\"0\"],[\"6f434c46\",\"0\"],[\"6f424946\",\"0\"]," +
                                 "[\"6f42414d\",\"0\"],[\"6f42474e\",\"0\"],[\"6f424d44\",\"0\"],[\"6f434446\",\"0\"]," +
                                 "[\"6f41464e\",\"0\"],[\"6f415747\",\"0\"],[\"6f42524c\",\"0\"],[\"6f42544e\",\"0\"]," +
                                 "[\"6f434f50\",\"0\"],[\"6f414544\",\"0\"],[\"6f425952\",\"0\"],[\"6f434144\",\"0\"]," +
                                 "[\"6f414c4c\",\"0\"],[\"6f424f42\",\"0\"],[\"6f434c50\",\"0\"],[\"6f415253\",\"0\"]," +
                                 "[\"6f425443\",\"0\"],[\"6f414e47\",\"0\"],[\"6f424244\",\"0\"],[\"6f424454\",\"0\"]," +
                                 "[\"6f424844\",\"0\"],[\"6f425750\",\"0\"],[\"6f42594e\",\"0\"],[\"6f434e59\",\"0\"]," +
                                 "[\"6f414d44\",\"0\"],[\"6f415544\",\"0\"],[\"6f415a4e\",\"0\"],[\"6f424e44\",\"0\"]," +
                                 "[\"6f414f41\",\"0\"],[\"6f435550\",\"0\"],[\"6f455552\",\"309000000\"]," +
                                 "[\"6f464a44\",\"0\"],[\"6f475451\",\"0\"],[\"6f454750\",\"0\"],[\"6f464b50\",\"0\"]," +
                                 "[\"6f474e46\",\"0\"],[\"6f484b44\",\"0\"],[\"6f484e4c\",\"0\"],[\"6f45524e\",\"0\"]," +
                                 "[\"6f455442\",\"0\"],[\"6f474250\",\"0\"],[\"6f474853\",\"0\"],[\"6f435645\",\"0\"]," +
                                 "[\"6f435a4b\",\"0\"],[\"6f445a44\",\"0\"],[\"6f474d44\",\"0\"],[\"6f435543\",\"0\"]," +
                                 "[\"6f47454c\",\"0\"],[\"6f474750\",\"0\"],[\"6f435243\",\"0\"],[\"6f444b4b\",\"0\"]," +
                                 "[\"6f444a46\",\"0\"],[\"6f444f50\",\"0\"],[\"6f474950\",\"0\"],[\"6f475944\",\"0\"]," +
                                 "[\"6f4b5257\",\"0\"],[\"6f4e4144\",\"0\"],[\"6f4e494f\",\"0\"],[\"6f4c414b\",\"0\"]," +
                                 "[\"6f4d5952\",\"0\"],[\"6f494452\",\"0\"],[\"6f494c53\",\"0\"],[\"6f4d4e54\",\"0\"]," +
                                 "[\"6f4d444c\",\"0\"],[\"6f485546\",\"0\"],[\"6f4d5552\",\"0\"],[\"6f4e474e\",\"0\"]," +
                                 "[\"6f494e52\",\"0\"],[\"6f4d5652\",\"0\"],[\"6f4d5a4e\",\"0\"],[\"6f4b4753\",\"0\"]," +
                                 "[\"6f485447\",\"0\"],[\"6f4c4250\",\"0\"],[\"6f4c564c\",\"0\"],[\"6f4d4144\",\"0\"]," +
                                 "[\"6f49534b\",\"0\"],[\"6f4b5057\",\"0\"],[\"6f4c544c\",\"0\"],[\"6f4d4b44\",\"0\"]," +
                                 "[\"6f4b4d46\",\"0\"],[\"6f4d4741\",\"0\"],[\"6f4b5944\",\"0\"],[\"6f4c4b52\",\"0\"]," +
                                 "[\"6f4c534c\",\"0\"],[\"6f4d4d4b\",\"0\"],[\"6f4d574b\",\"0\"],[\"6f4d584e\",\"0\"]," +
                                 "[\"6f4d4f50\",\"0\"],[\"6f4d524f\",\"0\"],[\"6f4c5244\",\"0\"],[\"6f4c5944\",\"0\"]," +
                                 "[\"6f4e4f4b\",\"0\"],[\"6f4a4550\",\"0\"],[\"6f4a4f44\",\"0\"],[\"6f4b5744\",\"0\"]," +
                                 "[\"6f584146\",\"0\"],[\"6f554758\",\"8635000000000\"],[\"6f5a4152\",\"0\"]," +
                                 "[\"6f584f46\",\"0\"],[\"6f534352\",\"0\"],[\"6f534c4c\",\"0\"],[\"6f545a53\",\"0\"]," +
                                 "[\"6f534447\",\"0\"],[\"6f535a4c\",\"0\"],[\"6f525746\",\"400000000000\"]," +
                                 "[\"6f4a5059\",\"0\"],[\"6f534f53\",\"0\"],[\"6f5a4d57\",\"4673267327\"]]";
        assertEquals(Helper.parseBalancesArray(JSON.parseArray(response)), expected);
    }

    @Test
    public void parseBalancesArrayOntologyPumpkin() throws SDKException {
        // For AMQgcrvnpPWnbAjps3Aj1bTfXSsYEYjXUg
        final String response = "[\"09\",\"\",\"0b\",\"06\",\"0a\",\"0a\",\"08\",\"\"]";
        final String expected = "[[\"00\",\"9\"],[\"01\",\"0\"],[\"02\",\"11\"],[\"03\",\"6\"],[\"04\",\"10\"]," +
                                 "[\"05\",\"10\"],[\"06\",\"8\"],[\"07\",\"0\"]]";
        assertEquals(Helper.parseBalancesArray(JSON.parseArray(response)), expected);
    }

    @Test
    public void bigInt2Bytes() {
        assertArrayEquals(new byte[] {-85, -86, -86, -86, -86, -86, -85, -128}, BigIntToNeoBytes(new BigInteger("-9175052165852779861")));

        assertArrayEquals(new byte[] {85, 85, 85, 85, 85, 85, 84, 127}, BigIntToNeoBytes(new BigInteger("9175052165852779861")));

        assertArrayEquals(new byte[] {85, 85, 85, 85, 85, 85, 84, -128}, BigIntToNeoBytes(new BigInteger("-9199634313818843819")));

        assertArrayEquals(new byte[] {-85, -86, -86, -86, -86, -86, -85, 127}, BigIntToNeoBytes(new BigInteger("9199634313818843819")));

        assertArrayEquals(new byte[] {16, 31, -128}, BigIntToNeoBytes(new BigInteger("-8380656")));

        assertArrayEquals(new byte[] {-16, -32, 127}, BigIntToNeoBytes(new BigInteger("8380656")));

        assertArrayEquals(new byte[] {16, 31, 127, -1}, BigIntToNeoBytes(new BigInteger("-8446192")));

        assertArrayEquals(new byte[] {-16, -32, -128, 0}, BigIntToNeoBytes(new BigInteger("8446192")));

        assertArrayEquals(new byte[0], BigIntToNeoBytes(new BigInteger("-0")));

        assertArrayEquals(new byte[0], BigIntToNeoBytes(new BigInteger("0")));
    }

    @Test
    public void bytes2BigInt() {
        assertEquals(BigIntFromNeoBytes(new byte[] {-85, -86, -86, -86, -86, -86, -85, -128}), new BigInteger("-9175052165852779861"));

        assertEquals(BigIntFromNeoBytes(new byte[] {85, 85, 85, 85, 85, 85, 84, 127}), new BigInteger("9175052165852779861"));

        assertEquals(BigIntFromNeoBytes(new byte[] {85, 85, 85, 85, 85, 85, 84, -128}), new BigInteger("-9199634313818843819"));

        assertEquals(BigIntFromNeoBytes(new byte[] {-85, -86, -86, -86, -86, -86, -85, 127}), new BigInteger("9199634313818843819"));

        assertEquals(BigIntFromNeoBytes(new byte[] {16, 31, -128}), new BigInteger("-8380656"));

        assertEquals(BigIntFromNeoBytes(new byte[] {-16, -32, 127}), new BigInteger("8380656"));

        assertEquals(BigIntFromNeoBytes(new byte[] {16, 31, 127, -1}), new BigInteger("-8446192"));

        assertEquals(BigIntFromNeoBytes(new byte[] {-16, -32, -128, 0}), new BigInteger("8446192"));

        assertEquals(BigIntFromNeoBytes(new byte[0]), new BigInteger("0"));
    }
}