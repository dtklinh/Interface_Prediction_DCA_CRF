The MATLAB code calculate_evolutionary_constraints.m defines
the function calculate_evolutionary_constraints, which takes
three arguments:
  1. a path/filename string for the FASTA alignment file
  2. an protein sequence identifier for the sequence of study
        as it appears in the alignment file.
  3. a path/filename string for the output file, which will
        be written by the function

The function may be called within the MATLAB interpreter like
this:

>> calculate_evolutionary_constraints('PF00071_v25.fa','RASH_HUMAN','your_output_filename.txt');

Example input and output files are included in this archive.

The FASTA alignment file should be gapped such that each index
position into any sequence record is considered to be aligned
to the same index position in all other sequences.

Upper case standard one letter amino acid codes in the
sequence of study determine the columns which can participate
in constraint pairing. Positions of lower case letters and
gaps can not.

If sequences contain non-standard or ambiguous amino acid
codes {B,Z,J,X,U,O}, they may either be handled as equivalent
to gaps (by setting environment variable
DI_METHOD_TO_RESOLVE_AMBIGUOUS_RESIDUES to '1') or may be
handled by excluding from the alignment all sequences
containing such codes (by setting environment variable
DI_METHOD_TO_RESOLVE_AMBIGUOUS_RESIDUES to '2')

Environment variable DI_THETA may be set to a value between
0.0 and 1.0. The higher this value, the wider the sequence
space distance limit used for counting sequence members
in the same neighborhood. The number of neighbors determines
the degree to which a member's influence is deflated in the
constraint score.

Environment variable DI_PSEUDOCOUNT_WEIGHT may be set to use
alternate values for creating pseudocounts in the co-occurence
frequency matrices.
