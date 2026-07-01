import cv2
import matplotlib.pyplot as plt
import numpy as np

def showImage(*imgs, axis = 'on'):
    num_images = len(imgs)
    fig, axes = plt.subplots(1, num_images, figsize=(15, 5))
    if num_images == 1:
        axes = [axes]
    for i, img in enumerate(imgs):
        rgbImage = cv2.cvtColor(img, cv2.COLOR_BGR2RGB) 
        axes[i].imshow(rgbImage)
        axes[i].axis(axis) 
    plt.show()

def printRGB(image, axis = 'on'):
    rgbImage = cv2.cvtColor(image, cv2.COLOR_BGR2RGB) # Convert to RGB
    plt.imshow(rgbImage)
    plt.axis(axis)
    plt.show()
    return rgbImage

def printGRAY(image, axis='on'):
    grayImage = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    plt.imshow(grayImage, cmap ='gray')
    plt.axis(axis)
    plt.show()
    return grayImage

def plotHistGrayImage(img, title='Histogram for gray channel'):
    hist = cv2.calcHist([img], channels=[0], mask=None, histSize=[256], ranges=[0,256])
    plt.figure() 
    plt.xlabel('Pixel Value')
    plt.ylabel('Frequency')
    plt.title(title)
    plt.plot(hist) 
    plt.xlim([0,256]) 
    plt.show()

def plotHistColorImage(rgbImage, title='Histogram for color image'):
    rHist = cv2.calcHist([rgbImage], channels=[0], mask=None, histSize=[256], ranges=[0,256])
    gHist = cv2.calcHist([rgbImage], channels=[1], mask=None, histSize=[256], ranges=[0,256])
    bHist = cv2.calcHist([rgbImage], channels=[2], mask=None, histSize=[256], ranges=[0,256])
    
    plt.plot(rHist, color='red')
    plt.plot(gHist, color='green')
    plt.plot(bHist, color='blue')
    
    plt.xlim([0,256])
    plt.xlabel('Pixel Value')
    plt.ylabel('Frequency')
    plt.title(title)
    plt.show()
