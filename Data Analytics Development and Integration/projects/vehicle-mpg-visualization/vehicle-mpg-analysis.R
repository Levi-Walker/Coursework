library(ggplot2)
library(dplyr)
library(gapminder)

mpgDF <- mpg

# Visualize engine displacement against highway fuel economy
ggplot(mpgDF,aes(displ,hwy, color = class)) +
  geom_point(size=2, shape=16) +
  theme_classic() +
  labs(title = 'Engine Size vs Highway MPG')


# Visualize the distribution of highway MPG
ggplot(mpgDF,aes(x=hwy)) +
  geom_histogram(bins = 10) +
  labs(title = 'Distribution of HWY MPG')


# Compare highway MPG distributions across drivetrain types
ggplot(mpgDF,aes(hwy, fill = drv)) +
  geom_density(alpha = 0.33) +
  labs(title = 'Density Plot of highway MPG with distinguishable drivetrain')


# Count vehicles by class
ggplot(mpgDF,aes(class)) +
  geom_bar() +
  labs(title = 'Count of Vehicles by Class')


# Compare vehicle classes and drivetrain types with stacked bars
ggplot(mpgDF,aes(class, fill=drv)) +
  geom_bar() +
  labs(title = 'Count of Vehicles by Class and Drivetrain')


# Compare vehicle classes and drivetrain types with grouped bars
ggplot(mpgDF,aes(class, fill=drv)) +
  geom_bar(position = "dodge") +
  labs(title = 'Vehicle Class With Clustered Drivetrain Types')


# Show average highway MPG by year
ggplot(mpgDF,aes(year, hwy)) +
  stat_summary(fun = mean, geom = "line")+
  labs(title = 'AVG HWY MPG Per Year')


# Compare highway MPG distributions by vehicle class
ggplot(mpgDF,aes(class, hwy)) +
  geom_violin() +
  labs(title = 'Distribution of Highway MPG by Class')


# Compare engine displacement and highway MPG by drivetrain type
ggplot(mpgDF,aes(displ, hwy, color=class)) +
  geom_point(size=2,shape=16)+
  labs(title = "Engine Size vs Highway MPG") +
  facet_wrap(~drv)


# Analytical observations
# Highway MPG decreases as engine displacement increases
ggplot(mpgDF,aes(displ, hwy)) +
  stat_summary(fun = mean, geom = "line")


# Compact vehicles have the highest average highway MPG
mpgDF |>
  group_by(class) |>
  summarize(mean_mpg = mean(hwy)) |>
  ggplot(aes(class, mean_mpg)) +
  geom_col() +
  labs(x = "Vehicle Class", y = "Average Highway MPG")


# Front-wheel-drive vehicles have the highest average highway MPG
mpgDF |>
  group_by(drv) |>
  summarize(mean_mpg = mean(hwy)) |>
  ggplot(aes(drv, mean_mpg)) +
  geom_col() +
  labs(x = "Vehicle Class", y = "Average Highway MPG")


# A density plot best communicates the MPG distribution
ggplot(mpgDF,aes(hwy,)) +
  geom_density()


