# Obstacle-Map
This proyect create a random obstacle map (matix) where obstacles are generated with a 100% if there are (0 &lt; neighbours obstacle &lt;= MAX_NEIGHBOURS), and with a dinamic probability that decrease exponentially with the number of obstacles alredy placed. This result in a very nice obstacle distribution in O(rows * cols) time guarantee.
