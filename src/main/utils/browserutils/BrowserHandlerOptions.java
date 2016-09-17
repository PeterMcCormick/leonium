package main.utils.browserutils;

public class BrowserHandlerOptions {
	public final BrowserOption<Boolean> screenshotOnClick = new BrowserOption<Boolean>();
	public final BrowserOption<Boolean> screenshotOnSelect = new BrowserOption<Boolean>();
	public final BrowserOption<Boolean> screenshotOnSendKeys = new BrowserOption<Boolean>();
	public final BrowserOption<Boolean> continueOnException = new BrowserOption<Boolean>();
	public final BrowserOption<Boolean> logOnWait = new BrowserOption<Boolean>();
	public final BrowserOption<Boolean> logAutonomously = new BrowserOption<Boolean>();
	public final BrowserOption<Integer> defaultWait = new BrowserOption<Integer>(15);

	public class BrowserOption<T> {
		private T value;

		public BrowserOption() {
		}

		public BrowserOption(T value) {
		}

		public void setValue(T value) {
			this.value = value;
		}

		public T getValue() {
			return this.value;
		}
	}
}