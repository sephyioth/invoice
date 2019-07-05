package com.genesis.liteutills.utils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * Created by hjy on 2015/12/23.
 */
public class JavaCTypeUtil {
    public static short getUnsignedByte(byte data) {      //将data字节型数据转换为0~255 (0xFF 即BYTE)。
        return (short) (data & 0x0FF);
    }

    public static int getUnsignedShort(short data) {      //将data字节型数据转换为0~65535 (0xFFFF 即 WORD)。
        return data & 0x0FFFF;
    }

    public static long getUnsignedInt(int data) {     //将int数据转换为0~4294967295 (0xFFFFFFFF即DWORD)。
        return data & 0x0FFFFFFFF;
    }

    public static String getStringfromByteBuffer(ByteBuffer bb) {
        if (bb == null) {
            return null;
        }
        String strrtn = "";
        byte[] b = new byte[1024];
        int icount = 0;
        while (bb.hasRemaining() && icount < 1024) {
            b[icount] = bb.get();
            if (b[icount] == 0) {
                break;
            }
            icount++;
        }
        if (icount > 0) {
            try {
                strrtn = new String(b, 0, icount, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return strrtn;
    }
}
