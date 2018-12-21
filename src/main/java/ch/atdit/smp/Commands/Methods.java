package ch.atdit.smp.Commands;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Methods {

	public Integer getLevel(Long onlineTime) {
		Integer divider = 60 * 1000; // 60 seconds
		DecimalFormat formatLevel = new DecimalFormat("#0");
		String groundLevel = formatLevel.format(Math.sqrt(onlineTime / divider)); // Exponential growth
		return Integer.parseInt(groundLevel) + 1;
	}

	public String convertToDate(Long timestamp) {
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss Z");
		return format.format(new Date(timestamp));
	}

	public String onlineTime(Long onlineTime, Long latestJoin) {
		Long ms;
		
		if (latestJoin == 0) {
			ms = onlineTime;
		} else {
			ms = onlineTime + (System.currentTimeMillis() - latestJoin);
		}
		String output = "";

		int days = (int) Math.floor(ms / 86400 / 1000);
		ms -= days * 86400 * 1000;

		int hours = (int) Math.floor(ms / 3600 / 1000);
		ms -= hours * 3600 * 1000;

		int minutes = (int) Math.floor(ms / 60 / 1000);
		ms -= minutes * 60 * 1000;

		int seconds = (int) Math.floor(ms / 1000);
		ms -= seconds * 1000;

		if (days > 1) {
			output += (days + " days, ");
		} else if (days == 1) {
			output += "1 day, ";
		}

		if (hours > 1) {
			output += (hours + " hours, ");
		} else if (hours == 1) {
			output += "1 hour, ";
		}

		if (minutes > 1) {
			output += (minutes + " minutes and ");
		} else if (minutes == 1) {
			output += "1 minute and ";
		}

		if (seconds > 1 || seconds == 0) {
			output += (seconds + " seconds");
		} else if (seconds == 1) {
			output += "1 second";
		}

		return output;
	}

	public String getKD(Double kills, Double deaths) {
		if (kills == 0 && deaths == 0) {
			return "0.00";
		} else if (kills > 0) {
			DecimalFormat decimalFormat0 = new DecimalFormat("#0.00");
			return decimalFormat0.format(kills);
		} else {
			DecimalFormat decimalFormat = new DecimalFormat("#0.00");
			return decimalFormat.format(kills / deaths);
		}
	}
	
	public String formatStats(String uuid) {
		return "";
	}
}
