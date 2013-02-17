import java.util.Date;


public class Meeting {
	private Dated startTime;
	private Dated endTime;

	public Meeting(Dated startTime, Dated endTime) {
		this.setStartTime(startTime);
		this.setEndTime(endTime);
	}

	@Override
	public String toString() {
		return this.getStartTime().toString() + " to " + this.getEndTime().toString();
	}

	public Dated getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Dated startTime) {
		this.startTime = startTime;
	}

	public Dated getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Dated endTime) {
		this.endTime = endTime;
	}

	@Override
	public boolean equals(Object other) {
		return this.getStartTime().equals(((Meeting) other).getStartTime()) &&
				this.getEndTime().equals(((Meeting) other).getEndTime());
	}
}
