"use client"
import * as React from "react"

import {Card, CardContent} from "@/components/ui/card"
import {Carousel, CarouselContent, CarouselItem, CarouselNext, CarouselPrevious,} from "@/components/ui/carousel"
import Autoplay from "embla-carousel-autoplay";


export function Ads() {
    return (
        <div className="flex flex-row w-full bg-white pt-10 pb-10 gap-20 pl-40 pr-40">
            <Carousel
                plugins={[
                    Autoplay({
                        delay: 4000, // 4 seconds delay between slides
                    }),
                ]}
                className="flex flex-row  w-3/4 max-h-[50vh] max-w-6xl">
                <CarouselContent>
                    {Array.from({length: 5}).map((_, index) => (
                        <CarouselItem key={index}>
                            <div className="p-1 ">
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