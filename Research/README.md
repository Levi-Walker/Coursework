# Brain Tumor Segmentation with U-Net

This project was completed by Levi Walker with research advisor Dr. Ackley Will through the Andrews University Computer Science Department. It was presented at the Andrews Honors and University Undergraduate Research Symposium and MBAAI.

We replicated a 2D U-Net for brain tumor segmentation, then tested whether small changes to data augmentation and segmentation threshold selection would improve its results.

![Research poster](./images/brain-tumor-unet-replication-poster.png)

## About the project

The model works with four types of brain MRI scans: T1, contrast-enhanced T1, T2, and FLAIR. A central slice from each scan is combined into a four-channel image, and the U-Net predicts a whole-tumor mask.

After establishing a baseline, we added a 5-degree random rotation to part of the training data and selected the segmentation threshold using the validation set. We repeated the experiment with multiple random seeds to check that the results were consistent.

## Results

Across 30 runs, the mean test Dice score increased from **0.672 ± 0.023** to **0.782 ± 0.043**. The updated model also converged sooner, averaging 43.4 ± 9.9 epochs compared with 49.7 ± 10.4 epochs for the baseline. The generalization gap remained small.

## Research materials

- [`brain-tumor-unet-replication.ipynb`](./brain-tumor-unet-replication.ipynb) — project notebook
- [`images/brain-tumor-unet-replication-poster.png`](./images/brain-tumor-unet-replication-poster.png) — research poster

## Acknowledgment

This work was supported by a scholarship from the Andrews University Undergraduate Research Scholarship (URS) program.

## Data

The MRI data came from the 2024 Brain Tumor Segmentation Challenge (BraTS).

- [BraTS 2024 challenge paper](https://arxiv.org/abs/2405.18368)
- [DOI: 10.48550/arXiv.2405.18368](https://doi.org/10.48550/arXiv.2405.18368)
