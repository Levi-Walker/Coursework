library(rvest)
library(dplyr)
library(ggplot2)


baseurl <- "https://books.toscrape.com/catalogue/page-"
BookContainerDF <- data.frame()

# Task 1: Web Scraping (Books Data)
for(x in 1:5){
  urlX <- paste0(baseurl, x)
  urlX <- paste0(urlX, ".html")
  
  B_pageX <- read_html(urlX)
  
  Title <- B_pageX |>
    html_elements("h3 a") |>
    html_attr("title")
  
  PriceGBP <- B_pageX |>
    html_elements(".price_color") |>
    html_text()
  
  
  tempDF <- data.frame(Title, PriceGBP)
  
  BookContainerDF <- bind_rows(BookContainerDF, tempDF)
}

# Confirm there are no missing values
summary(BookContainerDF)




# Task 2: Retrieve GBP exchange-rate data
url <- "https://x-rates.com/table/?from=GBP&amount=1"
page1 <- read_html(url)
theTables <- html_table(page1)
length(theTables)

RateTable <- theTables[[1]]

colnames(RateTable) <- c("Currancy", "FromGBP", "ToGBP")

PoundsRatesDF <- data.frame(RateTable)

summary(PoundsRatesDF)



# Task 3: Data Cleaning

# Remove the pound sign
BookContainerDF$PriceGBP = substring(BookContainerDF$PriceGBP, 2)

# Convert prices to numeric values
BookContainerDF <- BookContainerDF |>
  mutate(PriceGBP = as.numeric(PriceGBP))



# Task 4: Data Integration

# Get the exchange rate
xrate <- filter(PoundsRatesDF, Currancy == "US Dollar") |>
  pull(FromGBP)

xrate

# Add USD prices

BookContainerDF <- BookContainerDF |>
  mutate(PriceUSD = PriceGBP * xrate)

# Review the integrated data and confirm there are no missing values

BookContainerDF
summary(BookContainerDF)


# Categorize books as cheap, reasonable, or expensive by USD price

BookContainerDF <- BookContainerDF |>
  mutate(priceCategory = case_when(
    PriceUSD < 20 ~ "Cheap",
    PriceUSD <= 30 ~ "Reasonable",
    PriceUSD > 30 ~ "Expensive"
  ))



# Task 5: Data Analysis

# Identify the most and least expensive books
ExpensiveTitle <- BookContainerDF |>
  filter(PriceUSD == max(PriceUSD)) |>
  pull(Title)

print(ExpensiveTitle)


CheapTitle <- BookContainerDF |>
  filter(PriceUSD == min(PriceUSD)) |>
  pull(Title)

print(CheapTitle)


# Visualize the distribution of book prices
ggplot(BookContainerDF,aes(x=PriceUSD)) +
  geom_histogram(bins = 10) +
  labs(title = 'Distribution of Book Prices')

# Count books by price category
PriceByCatDF <- BookContainerDF |>
  count(priceCategory, sort = TRUE)
print(PriceByCatDF)


# Task 6: Visualization

# Compare prices
BookContainerDF$TitleShort <- substr(BookContainerDF$Title, 1, 20)

ggplot(BookContainerDF, aes(x = reorder(TitleShort, PriceUSD), y = PriceUSD)) +
  geom_col() +
  coord_flip() +
  labs(x = "Title", y = "Price (USD)")


# Distribution
ggplot(BookContainerDF,aes(x=PriceUSD)) +
  geom_histogram(bins = 10) +
  labs(title = 'Distribution of Book Prices', x="Price USD", y="Count")


# Count price categories
ggplot(BookContainerDF,aes(priceCategory)) +
  geom_bar() +
  labs(title = 'Count of Price Category', x="Price Category", y="Count")


# Write CSV

BookContainerDF <- BookContainerDF |>
  select(-TitleShort)

write.csv(BookContainerDF, file="Book Prices Dataset.csv")
