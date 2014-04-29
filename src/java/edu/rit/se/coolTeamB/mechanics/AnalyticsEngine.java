package edu.rit.se.coolTeamB.mechanics;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.Rotation;

import edu.rit.se.coolTeamB.core.LocalVend;
import edu.rit.se.coolTeamB.core.StatisticsReport;

public class AnalyticsEngine
{
    static private final long serialVersionUID = 1L;
    private int[] IDS;
    private StatisticsReport stats;
    private ArrayList<StatisticsReport> globalStats;
    private int globalSize;
    private Set<String> differentNames;
    
    /**
     * Constructor for the analytics engine. 
     * @param _IDS - Array of integers to populate the engine
     * @param _globalSize - Amount of vending machines available.
     */
    public AnalyticsEngine(int[] _IDS, int _globalSize)
    {
	IDS = _IDS;
	differentNames = new HashSet<String>();
	globalStats = new ArrayList<StatisticsReport>(_globalSize);
	globalSize = _globalSize;
	setNewIDS(IDS);
    }
    
    /**
     * Sets new ids for the analytics engine.
     * @param _IDS - The new IDS to update the analytics engine.
     */
    public void setNewIDS(int[] _IDS)
    {
	differentNames.clear();
	for (int i = 0; i < globalSize; ++i)
	{
	    globalStats.add(i, null);
	}
	for (int i = 0; i < _IDS.length; ++i)
	{
	    stats = new StatisticsReport(AnalyticsReader.getData(IDS[i]));
	    globalStats.add(IDS[i], stats);
	    for (String name: stats.getNames())
	    {
		differentNames.add(name);
	    }
	}
    }
    
    /**
     * Get the names of all the machines.
     * @return A set of string names.
     */
    public Set<String> getDifferentNames()
    {
	return differentNames;
    }
    
    /**
     * Generate the pie chart for the current machines.
     * @return A JFreeChart that is generated with the machines as 
     * the statistics wanted.
     */
    public JFreeChart generateMachineGrossPieChart()
    {
	HashMap<String, Double> differentItems = new HashMap<String, 
		Double>();

	for (StatisticsReport stats: globalStats)
	{
	    if (stats != null)
	    {
		for (int i = 0; i < stats.getSize(); ++i)
		{
		    if (differentItems.containsKey(stats.getNames().get(i)) &&
			    stats.getReasons().get(i) == ReasonRemoved.SOLD)
		    {
			differentItems.put(stats.getNames().get(i),
				differentItems.get(stats.getNames().get(i)) + 
				stats.getPrices().get(i));
		    }
		    else if (stats.getReasons().get(i) == ReasonRemoved.SOLD)
		    {
			differentItems.put(stats.getNames().get(i), 
				stats.getPrices().get(i));
		    }
		}
	    }
	}
	HashMap<String, Integer> differentItemsInt = 
			new HashMap<String, Integer>(differentItems.size());
	for (Map.Entry<String, Double> h : differentItems.entrySet())
	{
	    differentItemsInt.put(h.getKey(), ((Double)(h.getValue()*100)).intValue());
	}
	PieDataset dataSet = createPieDataset(differentItemsInt);
	JFreeChart chart = createPieChart(dataSet, "Items Gross Profit");
	return chart;
    }

    /**
     * Generates a pie chart of the items in machines.
     * @param name - The name of the item
     * @return A pie chart with the item statistics.
     */
    public JFreeChart generateItemPieChart(String name)
    {
	HashMap<ReasonRemoved, Integer> differentReasons = new 
		HashMap<ReasonRemoved, Integer>();
	HashMap<String, Integer> differentReasonsString = new
		HashMap<String, Integer>();
	for (StatisticsReport stats: globalStats)
	{
	    if (stats != null)
	    {
		for (int i = 0; i < stats.getSize(); ++i)
		{
		    if (stats.getNames().get(i).equals(name))
		    {
			if (differentReasons.containsKey(stats.getReasons().get(i)))
			{
			    differentReasons.put(stats.getReasons().get(i),
			    differentReasons.get(stats.getReasons().get(i)) + 1);
			}
			else
			{
			    differentReasons.put(stats.getReasons().get(i), 1);
			}
		    }
		}
	    }
	    for (Map.Entry<ReasonRemoved, Integer> h : differentReasons.entrySet())
	    {
		differentReasonsString.put(h.getKey().toString(), h.getValue());
	    }
	}
	PieDataset dataSet = createPieDataset(differentReasonsString);
	JFreeChart chart = createPieChart(dataSet, name + " : Reason Removed");
	return chart;
    }
    /**
     * Generates a chart of the income.
     * @return A chart with the the income used as the statistic to populate 
     * the pie graph.
     */
    public JFreeChart generateIncomeGraph()
    {
	XYDataset dataset = createDataSet();
	
	JFreeChart chart = ChartFactory.createTimeSeriesChart("Income vs. Time", 
		"Date", "Income", dataset, true, true, false);
	
	XYPlot plot = (XYPlot) chart.getPlot();
	plot.setBackgroundPaint(Color.lightGray);
	plot.setDomainGridlinePaint(Color.white);
	plot.setRangeGridlinePaint(Color.white);
	plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
	plot.setDomainCrosshairVisible(true);
	plot.setRangeCrosshairVisible(true);
	//plot.setDataset(dataset);
	//plot.setRangeAxis(da)
	
	XYItemRenderer r = plot.getRenderer();
	if (r instanceof XYLineAndShapeRenderer)
	{
	    XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
	    renderer.setBaseShapesVisible(true);
	    renderer.setBaseShapesFilled(true);
	    renderer.setDrawSeriesLineAsPath(true);
	}
	
	//plot.getRangeAxis().resizeRange(10.00);
	plot.getRangeAxis().resizeRange(15.00);
	
	//DateAxis axis = (DateAxis) plot.getDomainAxis();
	//axis.setDateFormatOverride(new SimpleDateFormat());
	
	
	return chart;
    }
    
    /**
     * Generates a chart depicting the most popular global item from 
     * all the machines.
     * @return A chart representing the most popular item sold. 
     */
    public JFreeChart globalPopularItem()
    {
	HashMap<String, Integer> globalItems = new HashMap<String, Integer>(globalStats.size());
	//for (int i = 0; i < IDS.length; ++i)
	//{
	    //stats = globalStats.get(IDS[i]);
	    globalItems = (HashMap<String, Integer>)
		    	  generateMachinePieChart(globalItems);
	//}
	PieDataset dataset = createPieDataset(globalItems);
	JFreeChart chart = createPieChart(dataset, "Items Sold");
	return chart;
    }
    
    /**
     * Generates the machine pie chart.
     * @return The generated pie chart as an object.
     */
    private Object generateMachinePieChart(
	    HashMap<String, Integer> differentItems)
    {
	for (StatisticsReport stats: globalStats)
	{
	    if (stats != null)
	    {
		for (int i = 0; i < stats.getSize(); ++i)
		{
		    if (differentItems.containsKey(stats.getNames().get(i)) &&
			    stats.getReasons().get(i) == ReasonRemoved.SOLD)
		    {
			differentItems.put(stats.getNames().get(i),
				differentItems.get(stats.getNames().get(i)) + 1);
		    }
		    else if (stats.getReasons().get(i) == ReasonRemoved.SOLD)
		    {
			differentItems.put(stats.getNames().get(i), 1);
		    }
		}
	    }
	}
	return differentItems;
    }

    private XYDataset createDataSet()
    {
	TimeSeries s1 = new TimeSeries("SVM U.S.");
	for (int i = 0; i < IDS.length; ++i)
	{
	    stats = globalStats.get(IDS[i]);
	    s1 = createSeries(s1);
	}
	TimeSeriesCollection dataset = new TimeSeriesCollection();
	dataset.addSeries(s1);
	return dataset;
    }
    
    private TimeSeries createSeries(TimeSeries s1)
    {
	if (stats != null)
	{
	    for (int i = 0; i < stats.getSize(); ++i)
	    {
		Date tempDate = stats.getActionDates().get(i).getTime();
		if (stats.getReasons().get(i) == ReasonRemoved.SOLD)
		{
		    if (s1.getDataItem(new Day(tempDate)) == null)
		    {
			s1.add(new Day(tempDate), stats.getPrices().get(i));
		    }
		    else
		    {
			s1.addOrUpdate(new Day(tempDate), s1.getDataItem(
				new Day(tempDate)).getValue().intValue() + 
				stats.getPrices().get(i));
		    }
		}
	    }
	    return s1;
	}
	else
	{
	    return null;
	}
    }

    private PieDataset createPieDataset(HashMap<String, Integer> differentItems)
    {
	DefaultPieDataset result = new DefaultPieDataset();
	for (Map.Entry<String, Integer> h : differentItems.entrySet())
	{
	    result.setValue(h.getKey(), h.getValue());
	}
	return result;
    }
    
    private JFreeChart createPieChart(PieDataset dataset, String title)
    {
	JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, 
							 true, false);
	PiePlot3D plot = (PiePlot3D) chart.getPlot();
	plot.setStartAngle(290);
	plot.setDirection(Rotation.CLOCKWISE);
	plot.setForegroundAlpha(0.5f);
	return chart;
    }
}
