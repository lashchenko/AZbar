LOCAL_PATH := $(call my-dir)
#APP_PATH:= $(NDK)/$(call my-dir) 

include $(CLEAR_VARS)

###LOCAL_MODULE_FILENAME := iconv
LOCAL_MODULE := libiconv 

LIBICONV := libiconv

LOCAL_CFLAGS := -I$(LOCAL_PATH)/$(LIBICONV)
LOCAL_SRC_FILES := iconv.c

include $(BUILD_STATIC_LIBRARY)

include $(CLEAR_VARS)

LOCAL_MODULE := zbar
###LOCAL_MODULE_FILENAME := zz
#LOCAL_C_INCLUDES := "include"
LOCAL_SRC_FILES := zbar/convert.c \
		zbar/decoder.c \
		zbar/error.c \
		zbar/image.c \
		zbar/img_scanner.c \
        zbar/refcnt.c \
        zbar/scanner.c \
        zbar/symbol.c \
        \
        zbar/qrcode/bch15_5.c \
        zbar/qrcode/binarize.c \
        zbar/qrcode/isaac.c \
        zbar/qrcode/qrdec.c \
        zbar/qrcode/qrdectxt.c \
        zbar/qrcode/rs.c \
        zbar/qrcode/util.c \
        \
        zbar/processor/null.c \
        zbar/decoder/qr_finder.c \
        \
        zbar/decoder/code128.c \
        zbar/decoder/code39.c \
        zbar/decoder/code93.c \
        zbar/decoder/ean.c \
        zbar/decoder/databar.c \
        zbar/decoder/i25.c \
        \
        com_blogspot_lashchenko_azbar_barcode_AZbar.c

LOCAL_CFLAGS := -I$(LOCAL_PATH) -I$(LOCAL_PATH)/include -I$(LOCAL_PATH)/zbar 
LOCAL_LDLIBS := -llog

LOCAL_STATIC_LIBRARIES := libiconv

include $(BUILD_SHARED_LIBRARY)
