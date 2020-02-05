package de.gesundkrank.jskills.trueskill.layers;

import de.gesundkrank.jskills.GameInfo;
import de.gesundkrank.jskills.factorgraphs.DefaultVariable;
import de.gesundkrank.jskills.factorgraphs.Variable;
import de.gesundkrank.jskills.numerics.GaussianDistribution;
import de.gesundkrank.jskills.trueskill.DrawMargin;
import de.gesundkrank.jskills.trueskill.TrueSkillFactorGraph;
import de.gesundkrank.jskills.trueskill.factors.GaussianFactor;
import de.gesundkrank.jskills.trueskill.factors.GaussianGreaterThanFactor;
import de.gesundkrank.jskills.trueskill.factors.GaussianWithinFactor;

public class TeamDifferencesComparisonLayer extends
    TrueSkillFactorGraphLayer<Variable<GaussianDistribution>, GaussianFactor, DefaultVariable<GaussianDistribution>> {

    private final double epsilon;
    private final int[] teamRanks;

    public TeamDifferencesComparisonLayer(TrueSkillFactorGraph parentGraph, int[] teamRanks, int totalNumberOfPlayers) {
        super(parentGraph);
        this.teamRanks = teamRanks;
        GameInfo gameInfo = parentFactorGraph.getGameInfo();
        epsilon = DrawMargin.getDrawMarginFromDrawProbability(gameInfo.getDrawProbability(), gameInfo.getBeta(), totalNumberOfPlayers);
    }

    @Override
    public void buildLayer() {
        for (int i = 0; i < getInputVariablesGroups().size(); i++) {
            boolean isDraw = teamRanks[i] == teamRanks[i + 1];
            Variable<GaussianDistribution> teamDifference = getInputVariablesGroups().get(i).get(0);

            GaussianFactor factor =
                isDraw
                        ? new GaussianWithinFactor(epsilon, teamDifference)
                    : new GaussianGreaterThanFactor(epsilon, teamDifference);

            AddLayerFactor(factor);
        }
    }
}