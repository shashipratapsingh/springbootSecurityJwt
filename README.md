# springbootSecurityJwt
It is the springboot security perpose developed jwt role base authentication
I have created the spring boot security which is on the jdk 8 and spring version is 2X.
created the two role user and admin.

#spring architecher sturucture explaination.

Vary first have to create the CorsConfiguration.
then WebSecurityConfiguration and JwtRequestFilter & JwtAuthenticationEntryPoint it will help to configuration in the jwt impalitation

then JwtService -> JWtUtil->JwtRequest->JwtResponse->JwtController

JwtController->JwtService->jWtUtil->userDao->WebSecurityConfiguration->jwtAuthenticationEntryPoint->jwtRequestFilter->userDetailsService

these all process have to pass

