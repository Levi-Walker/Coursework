# 1 Libraries
library(dplyr)
library(lubridate)
library(tidyr)


# 2 Get Data

# Create variable for data path (easier for reuse)
test_1_data_csv <- file.choose()
life_expectancy <- file.choose()

# Import csvs to dataframes
data_df <- read.csv(test_1_data_csv)
life_df <- read.csv(life_expectancy)


# 3 Examine the first 10 rows
head(data_df, 10)
head(life_df, 10)


# 4 Combine the datasets
# Keep all entries in life_df (exclude data_df countries not in life expectancy)

lwdf <- data_df |>
  left_join(life_df, by = "country")

# 5 Confirm and normalize numeric columns

# BMI
typeof(lwdf$bmi) # Confirmed as double
# Normalize BMI to a numeric type
lwdf$bmi <- as.numeric(lwdf$bmi)
lwdf$bmi

# Confirm the remaining analytical columns are numeric
typeof(lwdf$child_mortality)
typeof(lwdf$children_per_woman)
typeof(lwdf$co2_emissions)
typeof(lwdf$health_spending)
typeof(lwdf$poverty)
typeof(lwdf$Income)
typeof(lwdf$literacy_rate)

# Normalize analytical columns to numeric types
lwdf$child_mortality <- as.numeric(lwdf$child_mortality)
lwdf$children_per_woman <- as.numeric(lwdf$children_per_woman)
lwdf$co2_emissions <- as.numeric(lwdf$co2_emissions)
lwdf$health_spending <- as.numeric(lwdf$health_spending)
lwdf$poverty <- as.numeric(lwdf$poverty)
lwdf$Income <- as.numeric(lwdf$Income) # Converts integer values to double precision
lwdf$literacy_rate <- as.numeric(lwdf$literacy_rate)

# 6 Summarize distributions and missing values
summary(lwdf)

# 7 Remove poverty and literacy-rate columns
# Check columns before removal
colnames(lwdf)

# Remove columns
lwdf <- lwdf |>
  select(-poverty, -literacy_rate)

# Confirm the columns were removed
colnames(lwdf)


# 8 Remove rows where income is unknown

# Check for missing income values
is.na(lwdf$Income) # No income values are missing

# Remove observations with missing income
lwdf <- lwdf |>
  filter(!is.na(Income))


# 9 Replace missing fertility values with zero
lwdf <- lwdf |>
  mutate(children_per_woman = if_else(is.na(children_per_woman),0,children_per_woman))




# 10 Add a country-abbreviation column
lwdf <- lwdf |>
  mutate(country_abbr = toupper(substr(country,0,3)))




# 11 Replace missing health-spending values with the median
lwdf <- lwdf |>
  mutate(health_spending = if_else(is.na(health_spending),median(health_spending, na.rm = TRUE),health_spending))


# 12 Normalize UN membership dates to YYYY-MM-DD
lwdf <- lwdf |>
  mutate(un_member_since = dmy(un_member_since)) |>
  mutate(un_member_since = ymd(un_member_since))

# 13 Set Palestine's UN observer-state date
lwdf <- lwdf |>
  mutate(un_member_since = if_else(country == "Palestine",ymd("2012/11/29"),un_member_since))


# Analytical trends

# Life expectancy and CO2 emissions have a slight positive association

# 1 Life expectancy versus CO2 emissions
plot(lwdf$life_expectancy, lwdf$co2_emissions,
     xlab = "Lif Exp", ylab = "Co2", main = "Life Exp vs Co2")

# 2 Income by World Bank region
barplot(lwdf$Income, names.arg = lwdf$world_bank_region,
        main = "Income by Bank Region", ylab = "Income", xlab = "Bank Region")

# 3 Income versus health spending
plot(lwdf$Income, lwdf$health_spending,
     xlab = "Income", ylab = "Health Spending", main = "Income vs Health Spending")
# Income and health spending show a positive association

# 4 Child mortality distribution
hist(lwdf$child_mortality, xlab = "Child Mortality", main = "Child Mortality Frequencies")





























