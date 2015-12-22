module Geometry.Sphere (volume,sumArea) where

volume :: Float -> Float
volume radius = (4.0 / 3.0) * pi * (radius ^ 3)

sumArea :: Float -> Float
sumArea radius = 4 * pi * (radius ^ 2)
