import java.util.Date;


public class Meeting {
	private Date startTime;
	private Date endTime;

	public Meeting(Date startTime, Date endTime) {
		this.setStartTime(startTime);
		this.setEndTime(endTime);
	}

	@Override
	public String toString() {
		return this.getStartTime().toString() + " to " + this.getEndTime().toString();
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public boolean equals(Object other) {
		return this.getStartTime().equals(((Meeting) other).getStartTime()) &&
				this.getEndTime().equals(((Meeting) other).getEndTime());
	}
}
