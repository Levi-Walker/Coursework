# Install the required package
install.packages("dplyr")


# Load dplyr
library(dplyr)


Jan29_df <- starwars

Jan29_df[,c('name', 'height', 'mass')]

select(Jan29_df, name, height, mass)

# Exclude selected columns
select(Jan29_df, -name, -height, -mass)

# Select columns by name pattern
select(Jan29_df, contains('color'))


# Pipe operations using %>% (or |>)
Jan29_df %>%
  select(contains('color')) %>%
  filter(hair_color == "blond")


filter(select(Jan29_df,contains('color')), hair_color =='blond')

# Filter rows
filter(Jan29_df, name == 'Luke Skywalker')
filter(Jan29_df, mass >= 77)
filter(Jan29_df, eye_color == 'blue')
filter(Jan29_df, homeworld == 'Tatooine')


