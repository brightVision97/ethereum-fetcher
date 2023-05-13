package com.rachev.ethereumfetcher.util;

import com.rachev.ethereumfetcher.exception.RlpDecodingException;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;
import org.web3j.rlp.RlpDecoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.utils.Numeric;

import java.util.List;

@Component
public final class RlpDecoderUtil {

    private static final int TRANSACTION_HASH_LENGTH = 66;

    public List<String> decodeRlpToList(final String rlpHex) {
        if (StringUtils.isBlank(rlpHex)) {
            return List.of();
        }
        try {
            RlpList rlp = RlpDecoder.decode(Numeric.hexStringToByteArray(rlpHex));
            return ((RlpList) (rlp.getValues().get(0))).getValues().stream()
                    .map(it -> new String(((RlpString) it).getBytes()))
                    .filter(candidate -> candidate.startsWith("0x") && candidate.length() == TRANSACTION_HASH_LENGTH)
                    .toList();
        } catch (Exception e) {
            throw new RlpDecodingException(e.getLocalizedMessage(), e);
        }
    }
}
