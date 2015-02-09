package NMI;

/**
 *
 * @author tfra
 */

public class ColumnPair {

	private int columnOne;
	private int columnTwo;
	private double uValue;
	private String[] columns = new String[2];

	public ColumnPair( int columnIdentifierOne, int columnIdentifierTwo, double uValue, String columnOne, String columnTwo ) {
		this.columnOne = columnIdentifierOne;
		this.columnTwo = columnIdentifierTwo;
		this.uValue = uValue;
		this.columns[0] = columnOne;
		this.columns[1] = columnTwo;
	}

	public int[] getColumnNumbers() {
		int[] columnNumbers = new int[] {columnOne, columnTwo};
		return columnNumbers;
	}

	public double getValue() {
		return this.uValue;
	}

	public int[] getColumnNumbersReverted() {
		int[] columnsReverted = new int[] {columnTwo, columnOne};
		return columnsReverted;
	}

	public String[] getColumns() {
		return this.columns;
	}

}

