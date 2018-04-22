package com.cmcc.nativepackage;

/**
 */
public class Printer {

	static {
		System.loadLibrary("RR"); 
	}

	/**
	 * <b><i>public static native int openPrinter(int printerType,String
	 * deviceId,String password);</i></b>
	 * <p>
	 * 杩炴帴鎵撳嵃鏈�br>
	 * password鏄负浜嗗皢鏉ュ彲鑳藉嚭鐜扮殑wifi璁惧棰勭暀鐨勫弬鏁�
	 * 
	 * @param printerType 
              1锛歎SB鎵撳嵃鏈�鍖呮嫭USB鏅鸿兘缁堢澶栬涓�綋鏈轰腑鐨勬墦鍗版満)
              2锛氳摑鐗欐墦鍗版満锛堝寘鎷摑鐗欐櫤鑳界粓绔璁句竴浣撴満涓殑鎵撳嵃鏈猴級
              3锛氫覆鍙ｆ墦鍗版満锛堝寘鎷覆鍙ｆ櫤鑳界粓绔璁句竴浣撴満涓殑鎵撳嵃鏈猴級
              4锛氬唴缃墦鍗版満锛堝寘鎷櫤鑳界粓绔竴浣撴満涓殑鎵撳嵃鏈猴級
	 * @param deviceId
	 *            褰損rinterType涓�鏃讹紝deviceId涓烘墦鍗版満MAC鍦板潃銆�
                                                               褰損rinterType涓�鏃讹紝deviceId涓篣SB 鍗�绾�杞崲鐨勪覆鍙ｏ紝濡�dev/ttyUSB0

	 * @param password 棰勭暀瀛楁
	 * @return <li>0锛�Success锛涢潪0鐘舵�鐮侊紞>Fail

	 */
	public static native int openPrinter(int printerType, String deviceId,
			String password);

	/**
	 * <b><i>public static native int closePrinter();</i></b>
	 * <p>
	 * 鍏抽棴涓庢墦鍗版満鐨勮繛鎺�	 * 
	 * @return <i>0锛�Success锛涢潪0鐘舵�鐮侊紞>Fail

	 */
	public static native int closePrinter();

	/**
	 * <b><i>public static native int getPrinterVersion(byte[] version);</i></b>
	 * <p>
	 * 鑾峰彇鍚勫巶鍟嗘墦鍗版満缁勪欢鐨勭増鏈俊鎭�
	 * 
	 * @param version
	 *            鐗堟湰鍙凤細<i>鍚勫巶鍟嗘墦鍗版満缁勪欢鐨勭増鏈彿(涓変綅鏁存暟)锛屾暟鍊艰秺澶т唬琛ㄧ増鏈秺楂�/i>
	 * @return <i>0锛�Success锛涢潪0鐘舵�鐮侊紞>Fail
	 */
	public static native int getPrinterVersion(byte[] version);

	/**
	 * <b><i>public static native int initialPrinter();</i></b>
	 * <p>
	 * 鍒濆鍖栨墦鍗版満锛屾竻闄ゆ墦鍗扮紦鍐插尯涓殑鏁版嵁锛屽浣嶆墦鍗版満鎵撳嵃鍙傛暟鍒版墦鍗版満缂虹渷鍙傛暟<br>
	          涓嶆槸瀹屽叏鎭㈠鍒板嚭鍘傝缃紝鍙槸灏嗘墦鍗版寚浠ゅ弬鏁版仮澶嶅埌鎵撳嵃鏈虹己鐪佸弬鏁般�
                        鐐硅窛鏄寚鎵撳嵃鐨勫唴瀹规瘡涓偣涔嬮棿鐨勮窛绂汇�
                        鍏蜂綋缂虹渷璁剧疆鍖呮嫭濡備笅锛�
       1.瀛椾綋瀹介珮缂╂斁姣斾緥锛�锛�
       2.瀵归綈鏂瑰紡锛氬乏瀵归綈锛�
       3.宸﹁竟璺濓細0涓偣璺濓紱
       4.鍙宠竟璺� 0涓偣璺濓紱
       5.琛岄棿璺濓細8涓偣璺濓紱
       6.瀛楃闂磋窛锛�涓偣璺濓紱
       7.鎵撳嵃鏂瑰悜锛氭í鎵擄紱
       8.闈炵矖浣撴墦鍗帮紱
       9.闈炰笅鍒掔嚎鎵撳嵃锛�
       10.闈炲弽鐧芥墦鍗�

	 * 
	 * @return <i>0锛�Success锛涢潪0鐘舵�鐮侊紞>Fail
	 */
	public static native int initialPrinter();

	/**
	 * <b><i>public static native int setZoonIn(int widthZoonIn,int
	 * heightZoonIn);</i></b>
	 * <p>
	 * 璁剧疆鎵撳嵃鏈哄瓧绗︿覆鐨勫瓧绗﹀楂樼缉鏀炬瘮渚�br>
	 * 鎵撳嵃鏈哄瓧浣撳楂樼缉鏀炬瘮渚嬬己鐪佽缃负 1<br>
	 * 鍘傚晢蹇呴』鏀寔瀹介珮缂╂斁姣斾负姝ｅ父瀛椾綋涓ゅ�鎴栦互涓婏拷?
	 * 
	 * @param widthZoonIn
	 *            瀛椾綋鏀惧ぇ瀹藉害<i>鐩告瘮姝ｅ父瀛椾綋瀹藉害鐨勫�鏁帮紝蹇呴』鏄鏁存暟</i>
	 * @param heightZoonIn
	 *            瀛椾綋鏀惧ぇ楂樺害<i>鐩告瘮姝ｅ父瀛椾綋楂樺害鐨勫�鏁帮紝蹇呴』鏄鏁存暟</i>
	 * @return <li>0->Success锛涚姸鎬佺爜-> Fail
	 */
	public static native int setZoonIn(int widthZoonIn, int heightZoonIn);

	/**
	 * <b><i>public static native int setAlignType(int alignType);</i></b>
	 * <p>
	 * 璁剧疆鎵撳嵃鏈哄瓧绗︿覆鐨勫榻愭柟寮�br>
	 * <li>1.鎵撳嵃鏈虹己鐪佽缃负宸﹀榻�
	 * <li>2.浠呭湪涓�寮�澶勭悊鏃讹紝璇ュ懡浠ゆ墠鏈夋晥
	 * <li>3.绔栧悜鎵撳嵃涓嶆敮鎸佽缃榻愭柟寮忥紝榛樿涓婂榻�
	 * 
	 * @param alignType<i>
	 *            0锛氬乏瀵归綈
	 *            1锛氬眳涓榻�
	 *            2锛氬彸瀵归綈
	 * @return <li>0->Success锛涚姸鎬佺爜-> Fail
	 */
	public static native int setAlignType(int alignType);

	/**
	 * <b><i>public static native int setLeftMargin(int n);</i></b>
	 * <p>
	 * 璁剧疆鎵撳嵃鏈烘瘡琛屽瓧绗﹀乏杈硅窛涓簄涓偣璺�br>
	 * <li>1.宸﹁竟璺濅綅缃〃绀烘墦鍗板唴瀹圭殑宸︿晶杈圭紭浣嶇疆
	 * <li>2.鐐硅窛鏄寚鎵撳嵃鐨勫唴瀹规瘡涓偣涔嬮棿鐨勮窛绂�
	 * <li>3.绔栧悜鎵撳嵃涓嶆敮鎸佽缃榻愭柟寮忥紝榛樿涓婂榻�
	 * 
	 * @param n
	 *            宸﹁竟鐐硅窛
	 * @return <li>0锛�Success锛涢潪0鐘舵�鐮侊紞>Fail

	 */
	public static native int setLeftMargin(int n);

	/**
	 * <b><i>public static native int setRightMargin(int n);</i></b>
	 * <p>
	 * 璁剧疆鎵撳嵃鏈烘瘡琛屽瓧绗﹀彸杈硅窛涓簄涓偣璺�br>
	 * <li>1. 鍙宠竟璺濅綅缃〃绀烘墦鍗板唴瀹圭殑鍙充晶杈圭紭浣嶇疆
	 * <li>2. 鐐硅窛鏄寚鎵撳嵃鐨勫唴瀹规瘡涓偣涔嬮棿鐨勮窛绂�
	 * 
	 * @param n
	 *            鍙宠竟鐐硅窛
	 * @return <li>0锛�Success锛涢潪0鐘舵�鐮侊紞>Fail
	 */
	public static native int setRightMargin(int n);

	/**
	 * <b><i>public static native int setLineSpacingByDotPitch(int n);</i></b>
	 * <p>
	 * 璁剧疆鎵撳嵃鏈哄瓧绗︿覆鐨勫瓧绗﹁闂磋窛涓�n 涓瀭鐩寸偣璺�	 * <li>1.鎵撳嵃鏈鸿闂磋窛缂虹渷璁剧疆涓�
	 * <li>2.浠呭湪涓�寮�澶勭悊鏃讹紝璇ュ懡浠ゆ墠鏈夋晥
	 * <li>3.鐐硅窛鏄寚鎵撳嵃鐨勫唴瀹规瘡涓偣涔嬮棿鐨勮窛绂�
	 * 
	 * @param n
	 *            鍨傜洿鐐硅窛
	 * @return <li>0锛�Success锛�闈�鐘舵�鐮侊紞>Fail
	 */
	public static native int setLineSpacingByDotPitch(int n);

	/**
	 * <b><i>public static native int setWordSpacingByDotPitch(int n);</i></b>
	 * <p>
	 * 璁剧疆鎵撳嵃鏈哄瓧绗︿覆鐨勫瓧绗﹂棿璺濅负 n涓按骞崇偣璺�	 * <li>1.鎵撳嵃鏈哄瓧绗﹂棿璺濈己鐪佽缃负0
	 * <li>2.鍦ㄥ�瀹芥ā寮忎笅锛屽瓧绗﹀彸渚ч棿璺濇槸姝ｅ父鍊肩殑涓ゅ�銆傚綋瀛楃琚斁澶ф椂锛屽瓧绗﹀彸渚ч棿璺濊鏀惧ぇ鍚屾牱鐨勫�鏁般�璇ュ嚱鏁板悓鏃跺奖鍝嶈嫳鏂囧拰姹夊瓧瀛楃鐨勮瀹�
	 * <li>3.鐐硅窛鏄寚鎵撳嵃鐨勫唴瀹规瘡涓偣涔嬮棿鐨勮窛绂�
	 * 
	 * @param n
	 *            姘村钩鐐硅窛锛�鈮�n 鈮�255</i>
	 * @return <li>0锛�Success锛涢潪0鐘舵�鐮侊紞>Fail
	 */
	public static native int setWordSpacingByDotPitch(int n);

	/**
	 * <b><i>public static native int setPrintOrientation(int
	 * printOrientation);</i></b>
	 * <p>
	 * 璁剧疆鎵撳嵃鏈哄瓧绗︿覆鐨勬墦鍗版柟鍚�	 * <li>1.鎵撳嵃鏈虹己鐪佽缃负妯墦
	 * <li>2.璁剧疆鎵撳嵃鏂瑰悜鍚庯紝鎵�湁鐨勬墦鍗伴兘鎸夌収姝ゆ牸寮忔墦鍗�
	 * <li>3.绔栧悜涓嶆敮鎸佽缃榻愭柟寮忥紝榛樿涓婂榻愩�
	 * 
	 * @param printOrientation
	 *            鎵撳嵃鏂瑰悜<i>0锛氱珫鎵�  
	 *                    1锛氭í鎵�/i>
	 * @return <li>0锛�Success锛�  闈�鐘舵�鐮侊紞>Fail
	 */
	public static native int setPrintOrientation(int printOrientation);

	/**
	 * <b><i>public static native int setBold(int n);</i></b>
	 * <p>
	 * 璁剧疆鎵撳嵃鏈哄瓧绗︿覆鏄惁绮椾綋鎵撳嵃<br>
	 * 鎵撳嵃鏈虹己鐪佽缃负闈炵矖浣撴墦鍗�
	 * 
	 * @param n
	 *            鏄惁绮椾綋锛�i>0锛氬彇娑堢矖浣撴墦鍗拌缃紱1锛氳缃矖浣撴墦
	 * @return <li>0锛�Success锛�闈�鐘舵�鐮侊紞>Fail
	 */
	public static native int setBold(int n);

	/**
	 * <b><i>public static native int setUnderLine(int n);</i></b>
	 * <p>
	 * 璁剧疆鎵撳嵃鏈哄瓧绗︿覆鏄惁涓嬪垝绾挎墦
	 * 鎵撳嵃鏈虹己鐪佽缃负闈炰笅鍒掔嚎鎵撳嵃
	 * 
	 * @param n
	 *            鏄惁涓嬪垝绾挎墦鍗帮細<i>0锛氬彇娑堜笅鍒掔嚎鎵撳嵃    1锛氳缃笅鍒掔嚎鎵撳嵃</i>
	 * @return <li>0锛�Success锛�闈�鐘舵�鐮侊紞>Fail
	 */
	public static native int setUnderLine(int n);

	/**
	 * <b><i>public static native int setInverse(int n);</i></b>
	 * <p>
	 * 璁剧疆鎵撳嵃鏈哄瓧绗︿覆鏄惁鍙嶇櫧鎵撳嵃<br>
	 * 鎵撳嵃鏈虹己鐪佽缃负闈炲弽鐧芥墦鍗�
	 * 
	 * @param n
	 *            鏄惁鍙嶇櫧鎵撳嵃<i>0锛氬彇娑堝弽鐧芥墦鍗帮紱 1锛氳缃弽鐧芥墦</i>
	 * @return <li>0锛�Success锛�闈�鐘舵�鐮侊紞>Fail
	 */
	public static native int setInverse(int n);
	
	/**
	 * <b><i>public static native int print(String content);</i></b>
	 * <p>
	 * 鎵撳嵃瀛楃<br>
	 * 褰撴瘡琛屾暟鎹秴鍑烘墦鍗扮焊寮犲搴︽椂鎵撳嵃鏈鸿緭鍑鸿嚜鍔ㄦ崲琛�
	 * 
	 * @param content
	 *            鎵撳嵃瀛楃涓诧細<i>鎵撳嵃瀛楃涓诧紝鍙寘鎷琛屾墦鍗版暟鎹紝浣跨敤鈥漒n鈥濊〃绀烘崲琛�</i>
	 * @return <li>0锛�Success锛�闈�鐘舵�鐮侊紞>Fail
	 */
	public static native int print(String content); 
	
	/**
	 * <b><i>public static native int printHTML(String content);</i></b>
	 * <p>
	 * 鎵撳嵃HTML鏍煎紡鏁版嵁<br>
	 * 鏀寔甯哥敤html鏍囩
	 * 
	 * @param content
	 *            HTML鏍煎紡鏁版嵁
	 * @return <li>0锛�Success锛�闈�鐘舵�鐮侊紞>Fail
	 */
	public static native int printHTML(String content);
}
