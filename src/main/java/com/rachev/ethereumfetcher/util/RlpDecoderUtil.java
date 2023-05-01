package com.rachev.ethereumfetcher.util;

import org.springframework.stereotype.Component;
import org.web3j.rlp.RlpDecoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.utils.Numeric;

import java.util.List;

@Component
public class RlpDecoderUtil {

    public List<String> decodeRlpToList(final String rlpHex) {
        RlpList rlp = RlpDecoder.decode(Numeric.hexStringToByteArray(rlpHex));
        return ((RlpList) (rlp.getValues().get(0))).getValues().stream()
                .map(it -> new String(((RlpString) it).getBytes()))
                .toList();
    }
}
