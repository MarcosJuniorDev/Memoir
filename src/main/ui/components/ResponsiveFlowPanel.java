package main.ui.components;

import javax.swing.*;
import java.awt.*;

/**
 * Painel para uso em {@link JScrollPane} que mantém o posicionamento dos
 * componentes a cargo do {@link FlowLayout} e recalcula a altura depois que
 * os componentes quebram para uma nova linha.
 */
public class ResponsiveFlowPanel extends JPanel implements Scrollable {

    public ResponsiveFlowPanel(int alignment, int horizontalGap, int verticalGap) {
        super(new WrapFlowLayout(alignment, horizontalGap, verticalGap));
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 20;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return Math.max(1, visibleRect.height - 20);
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        if (getParent() instanceof JViewport viewport) {
            return getPreferredSize().height <= viewport.getHeight();
        }

        return false;
    }

    /**
     * A implementação padrão de FlowLayout calcula a altura como se todos os
     * componentes estivessem em uma única linha. Esta variação preserva o
     * algoritmo de posicionamento do FlowLayout e ajusta apenas esse cálculo.
     */
    private static class WrapFlowLayout extends FlowLayout {

        private WrapFlowLayout(int alignment, int horizontalGap, int verticalGap) {
            super(alignment, horizontalGap, verticalGap);
        }

        @Override
        public Dimension preferredLayoutSize(Container target) {
            return layoutSize(target, true);
        }

        @Override
        public Dimension minimumLayoutSize(Container target) {
            return layoutSize(target, false);
        }

        private Dimension layoutSize(Container target, boolean preferred) {
            synchronized (target.getTreeLock()) {
                Insets insets = target.getInsets();
                int horizontalGap = getHgap();
                int verticalGap = getVgap();
                int horizontalInsetsAndGaps = insets.left + insets.right + (horizontalGap * 2);
                int availableWidth = Math.max(0, getTargetWidth(target) - horizontalInsetsAndGaps);

                Dimension size = new Dimension();
                int rowWidth = 0;
                int rowHeight = 0;

                for (Component component : target.getComponents()) {
                    if (!component.isVisible()) {
                        continue;
                    }

                    Dimension componentSize = preferred
                            ? component.getPreferredSize()
                            : component.getMinimumSize();

                    if (rowWidth > 0 && rowWidth + componentSize.width > availableWidth) {
                        addRow(size, rowWidth, rowHeight, verticalGap);
                        rowWidth = 0;
                        rowHeight = 0;
                    }

                    if (rowWidth > 0) {
                        rowWidth += horizontalGap;
                    }

                    rowWidth += componentSize.width;
                    rowHeight = Math.max(rowHeight, componentSize.height);
                }

                addRow(size, rowWidth, rowHeight, verticalGap);
                size.width += horizontalInsetsAndGaps;
                size.height += insets.top + insets.bottom + (verticalGap * 2);

                return size;
            }
        }

        private int getTargetWidth(Container target) {
            if (target.getParent() instanceof JViewport viewport && viewport.getWidth() > 0) {
                return viewport.getWidth();
            }

            if (target.getWidth() > 0) {
                return target.getWidth();
            }

            return Integer.MAX_VALUE;
        }

        private void addRow(Dimension size, int rowWidth, int rowHeight, int verticalGap) {
            size.width = Math.max(size.width, rowWidth);

            if (size.height > 0) {
                size.height += verticalGap;
            }

            size.height += rowHeight;
        }
    }
}
