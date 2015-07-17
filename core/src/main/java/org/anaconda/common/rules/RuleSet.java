package org.anaconda.common.rules;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RuleSet<T> {

	private Set<Rule<T>> rules = new HashSet<Rule<T>>();

	public RuleSet(Collection<Rule<T>> rules) {
		this.rules.addAll(rules);
	}
	
	public RuleSet() {
	}

	public void addRule(Rule<T> rule) {
		rules.add(rule);
	}
	
	public boolean eval(T o) {
		return rules.stream().allMatch(r -> r.eval(o));
	}
}
