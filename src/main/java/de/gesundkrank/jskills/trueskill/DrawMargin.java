package de.gesundkrank.jskills.trueskill;

import de.gesundkrank.jskills.numerics.GaussianDistribution;

public final class DrawMargin {

    private DrawMargin() {
    }

    public static double getDrawMarginFromDrawProbability(double drawProbability, double beta, int totalPlayers) {

        /*
         * Derived from TrueSkill technical report (MSR-TR-2006-80), page 6
         *
         * draw probability = 2 * CDF(margin/(sqrt(n1+n2)*beta)) -1
         * implies
         * margin = inversecdf((draw probability + 1)/2) * sqrt(n1+n2) * beta
         * where n1 and n2 are the number of players on each team
         */
        return GaussianDistribution.inverseCumulativeTo(.5 * (drawProbability + 1), 0, 1) * Math.sqrt(totalPlayers) * beta;
    }
}