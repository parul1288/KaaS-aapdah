package beans;

public class AutoCompleteEntity {
	private final String label;
	private final String value;

	public AutoCompleteEntity(String _label, String _value) {
		super();
		this.label = _label;
		this.value = _value;
	}

	public final String getLabel() {
		return this.label;
	}

	public final String getValue() {
		return this.value;
	}
}