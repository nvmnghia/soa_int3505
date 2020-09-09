mvc vs presentation-business-data (n-tier)

mvc: triangle

       `Controller`
       /          \
      /            \
     /              \
`View` ------------ `Model`

`View` cannot talk to `Model` directly.

n-tier: linear

`UI` --- `Business` --- `Data`

The 2 architecture are not mutually exclusive.
