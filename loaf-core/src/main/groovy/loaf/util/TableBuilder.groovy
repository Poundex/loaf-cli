package loaf.util

import groovy.transform.Immutable
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType

/**
 * Created by poundex on 16/06/17.
 */
class TableBuilder
{
	private List<Tuple2<String, Map<String, Object>>> columns = []
	private List<List<Cell>> rows = []

	TableBuilder column(String title)
	{
		return column([:], title)
	}

	TableBuilder column(Map<String, Object> options, String title)
	{
		columns << new Tuple2<String, Map<String,Object>>(title, options)
		this
	}

	TableBuilder withRowBuilder(@ClosureParams(
			value = SimpleType,
			options = 'loaf.util.TableBuilder.RowBuilder') Closure closure)
	{
		RowBuilder rowBuilder = new RowBuilder()
		closure(rowBuilder)
		this
	}

	String build()
	{
		int[] colWidths = new int[columns.size()]
		columns.eachWithIndex { col, int i ->
			colWidths[i] = col.first.length()
		}
		rows.eachWithIndex { row, int i ->
			row.eachWithIndex { cell, int ci ->
				colWidths[ci] = Math.max(colWidths[ci], cell.content.length())
			}
		}
		for(int i = 0; i < colWidths.length; i++)
			colWidths[i] += columns[i].second.padRight ?: 2

		StringBuilder sb = new StringBuilder()
		columns.eachWithIndex { it, i -> sb << it.first.padRight(colWidths[i]) }
		sb << '\n'
		rows.each { row ->
			row.eachWithIndex { cell, int colIndex ->
				sb << (cell.formatter ?
						cell.formatter(cell.content.padRight(colWidths[colIndex])) :
						cell.content.padRight(colWidths[colIndex]))
			}
			sb << '\n'
		}
		return sb.toString()
	}

	private class RowBuilder
	{
		void row(Cell... cells)
		{
			rows << cells.toList()
		}

		Cell cell(String content,
		          @ClosureParams(value = SimpleType, options = 'java.lang.String')
				          Closure<String> formatter = null)
		{
			return new Cell(content ?: "", formatter)
		}
	}

	@Immutable
	private static class Cell
	{
		String content
		Closure<String> formatter
	}
}
