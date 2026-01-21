package Modal;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MoMoUtil {

	// --- CẤU HÌNH TÀI KHOẢN MOMO TEST (SANDBOX) ---
	// Bạn có thể giữ nguyên key này (Key test công khai của MoMo) hoặc đăng ký mới
	// tại business.momo.vn
	private static final String PARTNER_CODE = "MOMO";
	private static final String ACCESS_KEY = "F8BBA842ECF85";
	private static final String SECRET_KEY = "K951B6PE1waDMi640xX08PD3vg6EkVlz";
	private static final String ENDPOINT = "https://test-payment.momo.vn/v2/gateway/api/create";

	/**
	 * Tạo chữ ký HMAC SHA256 theo yêu cầu của MoMo
	 */
	public static String encode(String key, String data) throws Exception {
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
		sha256_HMAC.init(secret_key);
		byte[] bytes = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

	/**
	 * Gửi yêu cầu tạo thanh toán sang MoMo và lấy về Link thanh toán (payUrl)
	 */
	public static String createPayment(String orderId, String requestId, String amount, String orderInfo,
			String returnUrl, String notifyUrl) throws Exception {
		try {
			String requestType = "captureWallet";
			String extraData = "";

			// 1. Tạo chuỗi dữ liệu để ký (Phải đúng thứ tự a-z)
			String rawHash = "accessKey=" + ACCESS_KEY + "&amount=" + amount + "&extraData=" + extraData + "&ipnUrl="
					+ notifyUrl + "&orderId=" + orderId + "&orderInfo=" + orderInfo + "&partnerCode=" + PARTNER_CODE
					+ "&redirectUrl=" + returnUrl + "&requestId=" + requestId + "&requestType=" + requestType;

			// 2. Tạo chữ ký
			String signature = encode(SECRET_KEY, rawHash);

			// 3. Tạo JSON Request (Dùng Gson)
			JsonObject json = new JsonObject();
			json.addProperty("partnerCode", PARTNER_CODE);
			json.addProperty("partnerName", "Test MoMo");
			json.addProperty("storeId", "MomoTestStore");
			json.addProperty("requestId", requestId);
			json.addProperty("amount", amount);
			json.addProperty("orderId", orderId);
			json.addProperty("orderInfo", orderInfo);
			json.addProperty("redirectUrl", returnUrl);
			json.addProperty("ipnUrl", notifyUrl);
			json.addProperty("lang", "vi");
			json.addProperty("extraData", extraData);
			json.addProperty("requestType", requestType);
			json.addProperty("signature", signature);

			// 4. Gửi POST Request sang MoMo
			URL url = new URL(ENDPOINT);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.setDoOutput(true);

			try (OutputStream os = conn.getOutputStream()) {
				os.write(json.toString().getBytes(StandardCharsets.UTF_8));
			}

			// 5. Đọc phản hồi
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				try (Scanner scanner = new Scanner(conn.getInputStream())) {
					String responseBody = scanner.useDelimiter("\\A").next();
					// Parse JSON response để lấy payUrl
					JsonObject resJson = JsonParser.parseString(responseBody).getAsJsonObject();
					return resJson.get("payUrl").getAsString();
				}
			} else {
				throw new Exception("Lỗi kết nối MoMo: " + responseCode);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null; // Trả về null nếu lỗi
		}
	}
}