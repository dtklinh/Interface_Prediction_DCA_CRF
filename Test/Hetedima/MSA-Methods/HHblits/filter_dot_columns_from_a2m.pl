#!/usr/bin/perl -w
use strict;

my $filename = shift;
my $fasta_output_width = shift;
my $member_selection_identifier = shift;

if (!defined($filename)) {
	print STDERR "usage: filter_dot_columns_from_a2m.pl [fasta_col_width] [member_selection_id_prefix]\n";
	exit(1);
};
if (!defined($fasta_output_width)) {$fasta_output_width = 80;}

my @idline;
my @seqline;

my $seq;
open(INFILE,"<$filename") || die "error: cannot open file $filename";
while (my $line = <INFILE>) {
	chomp $line;
	if (substr($line,0,1) eq ">") {
		if (defined($seq)) {
			$seq =~ s/\s//g;
			push(@seqline,$seq);
		}
		$seq = "";
                $line =~ s/\r//g;
		push(@idline,$line);
	} else {
		if (defined($seq)) {
			$seq .= $line;
		}
	}
}
close(INFILE);
if (defined($seq)) {
	$seq =~ s/\s//g;
	push(@seqline,$seq);
}

if (scalar(@idline) == 0) {
	die "error: no sequences found\n";
}

if (scalar(@idline) != scalar(@seqline)) {
	die "error: file format --> sequence count does not match id count\n";
}

my $seq0 = $seqline[0];
my $rawseqwidth = length($seq0);
for (my $i = 1; $i < @seqline; $i++) {
	if (length($seqline[$i]) != $rawseqwidth) {
		die "error: file format --> length of first sequence ($rawseqwidth) does not match length of sequence $idline[$i]\n";
	}
}

my $reference_seq = $seq0;
my $reference_seq_index = 0;
if (defined($member_selection_identifier)) {
	my @matching_seq_indices;
	#find reference sequence by id
	for (my $i = 0; $i < @seqline; $i++) {
		my $thisid = substr($idline[$i],1);
		if ($thisid =~ m/^$member_selection_identifier/i) {
			push (@matching_seq_indices,$i);
		}
	}
	if (@matching_seq_indices == 0) {
		print STDERR "Error: no sequence identifier matching $member_selection_identifier was found\n";
		exit(1);
	}
	if (@matching_seq_indices > 1) {
		print STDERR "Error: multiple sequences in the alignment matched identifier: $member_selection_identifier:\n";
		foreach my $i (@matching_seq_indices) {
			print STDERR "\t" . $idline[$i] . "\n";
		}
		exit(1);
	}
	$reference_seq_index = $matching_seq_indices[0];
	$reference_seq = $seqline[$reference_seq_index];
}

my @usecolumn;
for (my $p = 0; $p < $rawseqwidth; $p++) {
	my $character = substr($reference_seq,$p,1);
	$usecolumn[$p] = ($character ne "." and $character ne "-");
}

#print reference sequence
print $idline[$reference_seq_index] . "\n";
my $thisseq = $seqline[$reference_seq_index];
my $column = 0;
for (my $p = 0; $p < $rawseqwidth; $p++) {
	if ($usecolumn[$p]) {
		print substr($thisseq,$p,1);
		$column++;
	}
	if ($column >= $fasta_output_width) {
		print "\n";
		$column = 0;
	}
}
if ($column > 0) {
	print "\n";
}

#print remaining sequences
for (my $i = 0; $i < @seqline; $i++) {
	if ($i == $reference_seq_index) {
		#don't print reference sequence again
		next;
	}
	print $idline[$i] . "\n";
	my $thisseq = $seqline[$i];
	my $column = 0;
	for (my $p = 0; $p < $rawseqwidth; $p++) {
		if ($usecolumn[$p]) {
			print substr($thisseq,$p,1);
			$column++;
		}
		if ($column >= $fasta_output_width) {
			print "\n";
			$column = 0;
		}
	}
	if ($column > 0) {
		print "\n";
	}
}

