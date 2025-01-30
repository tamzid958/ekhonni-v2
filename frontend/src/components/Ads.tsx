"use client"
import * as React from "react"
import {Card, CardContent} from "@/components/ui/card"
import {Carousel, CarouselContent, CarouselItem, CarouselNext, CarouselPrevious} from "@/components/ui/carousel"
import Autoplay from "embla-carousel-autoplay";

export function Ads() {
    const [carouselApi, setCarouselApi] = React.useState(null);
    const [selectedIndex, setSelectedIndex] = React.useState(0);

    React.useEffect(() => {
        if (!carouselApi) return;
        const onSelect = () => {
            setSelectedIndex(carouselApi.selectedScrollSnap());
        };
        onSelect();
        carouselApi.on("select", onSelect);
        return () => {
            carouselApi.off("select", onSelect);
        };
    }, [carouselApi]);

    return (
        <div className="flex flex-row w-full bg-white pt-10 pb-10 gap-20 pl-40 pr-40">
            <div className="w-3/4 max-h-[50vh] max-w-6xl flex flex-col items-center">
                <Carousel
                    plugins={[
                        Autoplay({
                            delay: 4000, // 4 seconds delay between slides
                        }),
                    ]}
                    className="w-full"
                    setApi={setCarouselApi}
                    opts={{
                        loop: true,
                    }}
                >
                    <CarouselContent>
                        {Array.from({length: 5}).map((_, index) => (
                            <CarouselItem key={index}>
                                <div className="p-1">
                                    <Card className="bg-brand-mid rounded-3xl overflow-hidden hover:bg-brand-light">
                                        <CardContent
                                            className="flex items-center rounded-3xl overflow-hidden justify-center h-[50vh] p-0">
                                            <img
                                                src={`ad${index + 1}.png`}
                                                alt={`public/AdsImage/ad ${index + 1}`}
                                                className="h-full w-full object-cover"
                                            />
                                        </CardContent>
                                    </Card>
                                </div>
                            </CarouselItem>
                        ))}
                    </CarouselContent>
                    <CarouselPrevious className={'invisible'}/>
                    <CarouselNext className={'invisible'}/>
                </Carousel>

                <div className="flex space-x-2 mt-1">
                    {Array.from({length: 5}).map((_, index) => (
                        <button
                            key={index}
                            className={`w-3 h-3 rounded-full transition-colors ${
                                selectedIndex === index
                                    ? "bg-brand-dark"
                                    : "bg-gray-300 hover:bg-gray-400"
                            }`}
                            onClick={() => carouselApi?.scrollTo(index)}
                        />
                    ))}
                </div>
            </div>

            <Carousel
                plugins={[
                    Autoplay({
                        delay: 3000, // 3 seconds delay between slides
                    }),
                ]}
                opts={{
                    align: "start",
                }}
                orientation="vertical"
                className="w-1/4 max-w-xl max-h-[440px] mt-6"
            >
                <CarouselContent className="-mt-1 h-[440px]">
                    {Array.from({length: 5}).map((_, index) => (
                        <CarouselItem key={index} className="pt-1 md:basis-1/2">
                            <div className="p-1">
                                <Card className="w-full h-52 bg-brand-mid rounded-3xl hover:bg-brand-light">
                                    <CardContent className="flex items-center justify-center h-[22vh] p-4">
                                        <img
                                            src={`ad${index + 1}.png`}
                                            alt={`public/AdsImage/ad ${index + 1}`}
                                            className="h-full w-full object-cover rounded-2xl"
                                        />
                                    </CardContent>
                                </Card>
                            </div>
                        </CarouselItem>
                    ))}
                </CarouselContent>
                <CarouselPrevious className={'invisible'}/>
                <CarouselNext className={'invisible'}/>
            </Carousel>
        </div>
    )
}
