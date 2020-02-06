package core;

import java.util.*;

public class xor extends func {

    public xor(func... array) {
        f = asList(array);
    }

    public xor(List<func> list) {
        f.addAll(list);
    }

    @Override
    protected boolean eq2(func other) {
        return isEq(f, other.f);
    }

    @Override
    public int total() {
        int total = 0;
        for (func term : f) {
            total += term.total();
        }
        return total;
    }

    @Override
    public func simplify() {
        return this;
    }

    @Override
    String toString2() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < f.size(); i++) {
            func term = f.get(i);
            if (!term.isCons() && !term.isXor() && !term.isVar()) {
                sb.append(term.top());
            } else {
                sb.append(term);
            }
            if (i < f.size() - 1) {
                sb.append(xorDel);
            }
        }
        return sb.toString();
    }

    @Override
    public func alternate() {
        // a' and b or a and b'
        func a = f.get(0);
        func b = f.size() == 2 ? f.get(1) : new xor(wout(0));

        a = a.alternate();
        b = b.alternate();
        func l = a.and(b.not());
        func r = a.not().and(b);
        return l.or(r);
    }

    @Override
    public func not() {
        return new xnor(f);
        // return alternate().not();
    }

    @Override
    public cons get(var[] v, cons[] c) {
        return alternate().get(v, c);
    }

    @Override
    public List<var> list() {
        return alternate().list();
    }
}
