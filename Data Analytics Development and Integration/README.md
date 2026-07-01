# Data Analytics Development and Integration

This course focused on using R to clean, combine, analyze, and visualize data. The folder contains four larger projects and several shorter exercises from the course.

## Projects

- [Global development analysis](./projects/global-development-analysis/global-development-analysis.R) joins development and life expectancy data, cleans dates and numeric fields, handles missing values, and compares country indicators. The supporting CSV files are included.
- [Book price web scraping](./projects/book-price-web-scraping/book-price-analysis.R) reads a paginated book catalog, retrieves a GBP exchange rate, converts prices to USD, and groups books by price.
- [Rental market web scraping](./projects/rental-market-web-scraping/rental-market-analysis.R) collects rental listings, cleans prices, converts currencies, and plots rental price groups.
- [Vehicle MPG visualization](./projects/vehicle-mpg-visualization/vehicle-mpg-analysis.R) compares fuel economy by engine size, vehicle class, drivetrain, and model year. The generated plots are included in the project's `figures/` folder.

## Smaller exercises

The `examples/` directory contains practice with R fundamentals, `dplyr` data wrangling, dates, base plots, and joins between patient, visit, billing, and laboratory datasets.

## What I learned

- Cleaning and reshaping tabular data
- Joining related datasets
- Scraping HTML tables and page elements
- Building plots with `ggplot2`
- Working with dates and missing values