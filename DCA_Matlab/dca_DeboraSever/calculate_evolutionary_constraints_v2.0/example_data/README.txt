PF00071_v25.fa is a sample sequence family alignment for
input into calculate_evolutionary_constraints.m

PF00071_P01112_MI_DI.txt contains the expected output
formatted into 6 fields.
The first 10 lines from the file:
5 K 6 L 0.32962 0.21692 
5 K 7 V 0.32305 0.121323 
5 K 8 V 0.255726 0.0287701 
5 K 9 V 0.249865 0.0129912 
5 K 10 G 0.227589 0.0535867 
5 K 11 A 0.244451 0.0105915 
5 K 12 G 0.24409 0.00981724 
5 K 13 G 0.231187 0.00992219 
5 K 14 V 0.226463 0.0115453 
5 K 15 G 0.213629 0.0356044 
...

Each line is a constraint pair. The fields are:
1. the first index position into the sequence of study
        (gaps are excluded)
2. the residue code at that index position
3. the second index position into the sequence of study
        (gaps are excluded)
4. the residue code at that index position
5. the MI score for this residue pair
6. the EC score for this residue pair
