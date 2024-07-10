/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseballElimination {
    private final int numberOfTeams;
    private final Map<String, Integer> teamMap;
    private final Map<Integer, String> teamIdMap;

    private final int[][] gamesAgaints;
    private final int[] wins;
    private final int[] loss;
    private final int[] r;

    public BaseballElimination(String filename) {
        In in = new In(filename);
        numberOfTeams = Integer.parseInt(in.readLine());
        teamMap = new HashMap<String, Integer>();
        teamIdMap = new HashMap<Integer, String>();
        gamesAgaints = new int[numberOfTeams][numberOfTeams];
        wins = new int[numberOfTeams];
        loss = new int[numberOfTeams];
        r = new int[numberOfTeams];

        int i = 0;
        while (!in.isEmpty()) {
            String line = in.readLine().trim();
            String[] parts = line.split(" +");
            wins[i] = Integer.parseInt(parts[1]);
            loss[i] = Integer.parseInt(parts[2]);
            r[i] = Integer.parseInt(parts[3]);

            teamMap.put(parts[0], i);
            teamIdMap.put(i, parts[0]);
            for (int j = 4; j < parts.length; j++) {
                gamesAgaints[i][j - 4] = Integer.parseInt(parts[j]);
            }
            i++;
        }

    }

    /**
     * @param team
     * @return
     * @see BaseballElimination#certificateOfElimination(String)
     */
    public boolean isEliminated(String team) {
        if (!teamMap.containsKey(team)) throw new IllegalArgumentException("Invalid team used");
        if (certificateOfElimination(team) == null) return false;
        return true;
    }

    /**
     * Use Ford Fulkerson to determine if this team is eliminated.
     * But we need to create a flow network first.
     * Let index(team) = x. So we have n-1 teams left.
     * if each of n-1 teams play against each other, we have (n-1)Combination 2
     * i.e (n-1)!/2!(n-3)! = (n-1)(n-2)/2 games against each other.
     *
     * @param team
     * @return subset R of teams that eliminates given team; null if not eliminated
     */
    public Iterable<String> certificateOfElimination(String team) {
        if (!teamMap.containsKey(team)) throw new IllegalArgumentException("Invalid team used");
        int n = numberOfTeams();
        int x = teamMap.get(team);
        List<String> cert = new ArrayList<>();
        // simple elimination
        int potentialWins = wins[x] + r[x];
        for (int v = 0; v < n; v++) {
            if (v != x) {
                if (potentialWins < wins[v]) {
                    cert.add(teamIdMap.get(v));
                }
            }
        }
        if (cert.size() > 0)
            return cert;
        int numberOfGamesAgaintsEachOther = (n - 1) * (n - 2) / 2;
        // 1 for source, 1 for sink/target,
        // and n-1 vertices to sink, but also include vertex x which is not used, for a total of n
        int V = numberOfGamesAgaintsEachOther + 1 + 1 + n;
        FlowNetwork G = new FlowNetwork(V);
        // team vertices go from 0 to n-1. so create other vertices from n to ...
        int s = n;
        int t = n + 1;
        int i = t;
        for (int j = 0; j < n; j++) {
            if (j == x) {
                continue;// don't include team x
            }
            else {
                G.addEdge(new FlowEdge(j, t, wins[x] + r[x] - wins[j]));
            }
            for (int k = j + 1; k < n; k++) {
                if (k == x) continue;
                int g = gamesAgaints[j][k];
                int vertex = ++i;
                G.addEdge(new FlowEdge(s, vertex, g));
                G.addEdge(new FlowEdge(vertex, j, Double.POSITIVE_INFINITY));
                G.addEdge(new FlowEdge(vertex, k, Double.POSITIVE_INFINITY));
            }
        }
        FordFulkerson ff = new FordFulkerson(G, s, t);
        ff.value();

        for (int v = 0; v < n; v++) {
            // do we need to exclude team x
            if (ff.inCut(v)) {
                cert.add(teamIdMap.get(v));
            }
        }
        if (cert.size() != 0)
            return cert;
        else
            return null;
    }

    public int numberOfTeams() {
        return numberOfTeams;
    }

    public Iterable<String> teams() {
        return teamMap.keySet();
    }

    public int wins(String team) {
        if (!teamMap.containsKey(team)) throw new IllegalArgumentException("Invalid team used");
        return wins[teamMap.get(team)];
    }

    public int losses(String team) {
        if (!teamMap.containsKey(team)) throw new IllegalArgumentException("Invalid team used");
        return loss[teamMap.get(team)];
    }

    public int remaining(String team) {
        if (!teamMap.containsKey(team)) throw new IllegalArgumentException("Invalid team used");
        return r[teamMap.get(team)];
    }

    public int against(String team1, String team2) {
        if (!teamMap.containsKey(team1) || !teamMap.containsKey(team2))
            throw new IllegalArgumentException("Invalid team used");
        int i = teamMap.get(team1);
        int j = teamMap.get(team2);
        return gamesAgaints[i][j];
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
