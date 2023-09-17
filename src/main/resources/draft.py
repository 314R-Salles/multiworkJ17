from PIL import Image, ImageDraw, ImageFont
import sys

pathToGod = r"/var/www/dist/kmpick/browser/assets/godsMedaillon"
pathToBackground = r"/var/www/dist/kmpick/browser/assets/gods/bg_krosmaga.jpg"
pathToFont = r"/var/www/dist/kmpick/browser/assets/BebasNeue.otf"


def makeImage(player1, gods1, player2, gods2, output):
    print player1
    im = Image.open(pathToBackground)
    if (len(player1) > 23):
        player1 = player1[0:22]

    if (len(player2) > 23):
        player2 = player2[0:22]

    width = 980
    height = 720
    fontSize = 100
    im = im.resize((width, height))
    dr = ImageDraw.Draw(im)
    resize = (150, 150)
    fontPlayer = ImageFont.truetype(pathToFont, fontSize)

    # Player 1 and gods
    w, h = dr.textsize(player1, font=fontPlayer)
    start = min((width-w)/2-20, 175)
    end = max(795, int((width-w)/2+w+20))

    dr.text(((width-w)/2, 10), player1, (255,255,255), font=fontPlayer)
    dr.line([((width-w)/2-10, 10+h/2), (start, 10+h/2), (start, 325), (end,325), (end,10+h/2), ((width-w)/2+w+10,10+h/2)],
            width=3, fill=(255,255,255))

    # 2 pick
    imCard = Image.open(f"{pathToGod}/{gods1[0]}.png")
    imCard = imCard.resize(resize)
    im.paste(imCard, (210, 150), mask=imCard)
    imCard = Image.open(f"{pathToGod}/{gods1[1]}.png")
    imCard = imCard.resize(resize)
    im.paste(imCard, (410, 150), mask=imCard)
    # 1 ban
    imCard = Image.open(f"{pathToGod}/{gods1[2]}.png").convert('LA')
    imCard = imCard.resize(resize)
    im.paste(imCard, (610, 150), mask=imCard)

    # Player 2 and gods
    w, h = dr.textsize(player2, font=fontPlayer)
    start = min((width-w)/2-20, 175)
    end = max(795, int((width-w)/2+w+20))

    dr.text(((width-w)/2, 350), player2, (255,255,255), font=fontPlayer)
    dr.line([((width-w)/2-10, 350+h/2), (start, 350+h/2), (start, 350+325), (end,350+325), (end,350+h/2), ((width-w)/2+w+10,350+h/2)],
            width=3, fill=(255,255,255))

    # 2 pick
    imCard = Image.open(f"{pathToGod}/{gods2[0]}.png")
    imCard = imCard.resize(resize)
    im.paste(imCard, (210, 490), mask=imCard)
    imCard = Image.open(f"{pathToGod}/{gods2[1]}.png")
    imCard = imCard.resize(resize)
    im.paste(imCard, (410, 490), mask=imCard)

    # 1 ban
    imCard = Image.open(f"{pathToGod}/{gods2[2]}.png").convert('LA')
    imCard = imCard.resize(resize)
    im.paste(imCard, (610, 490), mask=imCard)

    im.save(output)


def makeTemplate(output):
    im = Image.new(mode="RGB", size=(1280, 720))
    im.save(output)


if __name__ == "__main__":
    print "coucou"
    if len(sys.argv) == 1:
        makeTemplate(sys.argv[1])
    else:
        makeImage(sys.argv[1], [sys.argv[2], sys.argv[3], sys.argv[4]], sys.argv[5], [sys.argv[6], sys.argv[7], sys.argv[8]], sys.argv[9])
