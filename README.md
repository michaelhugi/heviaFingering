# heviaFingering
This JAVA command line application enables to auto create fingerings for the hevia bagpipes with open fingering patterns and their vibratos.

Actually it's fixed to a a-minor german bagpipe fully chromatic. Input possibilities for the user are prepared but not active.

## Fingering patterns
The application works with fingering patterns from left to right. Left is top of the chanter. 0 is closed 1 is open.

Examples: 

1 000 0000 1 is the pattern for the top hole open and the bottom hole open

0 001 1111 1 is the pattern for the top three holes open

## Vibrato mask
Similar to fingering patterns vibrato masks can be implemented for any fingering. This will auto create all possible vibratos based on a non linear approach for the pitch based on the pitch of the first hole covered in the vibrato.

Example 1:

0 001 1111 1 is the fingering pattern

0 000 1111 1 means that all fingers of the right hand can be used for vibrato

Example 2

0 010 1111 1 is the fingering pattern

0 000 1111 1 means that all fingers of the right hand can be used for vibrato

Example 3

0 000 1111 1 is the fingering pattern

0 000 0111 1 means that the bottom three fingers can be used for vibrato

### Vibrato priority
If vibratos are added for a fingering position, they will replace all other vibratos but not fingerings that are 'not vibratos'. So the order how the fingerings are added matters.