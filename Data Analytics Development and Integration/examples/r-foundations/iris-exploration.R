# Levi Walker
# January 15, 2026
# Exploring R Assignment

# Explore the iris dataset
data(iris)

head(iris, 6)
dim(iris)
str(iris)

# Summary statistics
mean(iris$Sepal.Length)
max(iris$Petal.Length)
length(unique(iris$Species))

# Visualization

plot(iris$Sepal.Length, iris$Petal.Length, xlab = 'Sepal Len', ylab = 'Petal Len')

# Interpretation
# The mean sepal length is approximately 5.84 units
# The maximum petal length is 6.9 units
# The dataset contains three species
# Sepal length and petal length have a positive correlation

# Compare petal and sepal length-width relationships

# Petal length and width have a strong positive correlation
plot(iris$Petal.Length, iris$Petal.Width, xlab = 'Petal Len', ylab = 'Petal Wid')

# Sepal length and width have a weak negative correlation
plot(iris$Sepal.Length, iris$Sepal.Width, xlab = 'Sepal Len', ylab = 'Sepal Wid')
